package me.bscal.mcbody.listeners;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.util.Vector;

import fr.loisduplain.api.raycast.*;
import fr.loisduplain.api.raycast.Raycast.RaycastType;
import me.bscal.mcbody.MCBody;
import me.bscal.mcbody.body.PartType;
import me.bscal.mcbody.entities.CombatData;

public class CombatListeners implements Listener
{

    private static final BlockFace[] AXIS = new BlockFace[4];
    private static final BlockFace[] RADIAL = {BlockFace.WEST, BlockFace.NORTH_WEST, BlockFace.NORTH, BlockFace.NORTH_EAST, BlockFace.EAST, BlockFace.SOUTH_EAST, BlockFace.SOUTH, BlockFace.SOUTH_WEST};
    static {
        for (int i = 0; i < AXIS.length; i++) {
            AXIS[i] = RADIAL[i << 1];
        }
    }

    private static final double RAY_LENGTH = 0.2;

    private static final double HEAD_OFFSET = 1.5;
    private static final double LEG_OFFSET = 0.8;

    private final CombatData m_data;

    public CombatListeners()
    {
        m_data = new CombatData();
    }

    @EventHandler
    public void OnEntityDamageByEntityEvent(EntityDamageByEntityEvent e)
    {
        if (e.getEntity().isDead()  || !(e.getEntity() instanceof LivingEntity)) return;
        if (!(e.getDamager() instanceof LivingEntity)) return;

        LivingEntity damager = (LivingEntity) e.getDamager();
        LivingEntity damagee = (LivingEntity) e.getEntity();
        DamageCause cause = e.getCause();
        double damage = e.getDamage();

        m_data.side = GetSide(damagee, damager);

        Vector moveVec = damagee.getLocation().getDirection();
        float angle = (float)Math.atan2(moveVec.getZ(), moveVec.getBlockX()) - 180f;
        m_data.face = AXIS[Math.round(angle / 90f) & 0x3];

        m_data.distance = damager.getLocation().distance(damagee.getLocation());
        Location loc = damager.getEyeLocation();
        m_data.ray = new Raycast(loc, m_data.distance + RAY_LENGTH);
        m_data.ray.setShowRayCast(MCBody.Debug);
        if (m_data.ray.compute(RaycastType.ENTITY))
        {
            // Hit
            Location hitLoc = m_data.ray.getHurtLocation();
            Location offset = damagee.getLocation().subtract(hitLoc);
            PartType type = GetPartTypeFromLoc(offset, m_data.face);
            if (MCBody.Debug)
                MCBody.PrintFormat("CombatListener", cause, damage, type, m_data.face, m_data.side, offset);

            if (damagee instanceof Player && MCBody.Get().GetPlayerPartManager().IsActive())
                MCBody.Get().GetPlayerPartManager().GetPlayer((Player) damagee).HandleDamage(e, type, m_data);

            if (MCBody.Get().GetEntityManager().IsActive())
                MCBody.Get().GetEntityManager().HandleDamage(e, cause, damage, type, m_data);
        }
    }

    private PartType GetPartTypeFromLoc(final Location offset, final BlockFace face)
    {
        // heights .4 .8 .6 , arms .6 / width .8 , arms .4
        if(offset.getY() < -HEAD_OFFSET) // Head shot
            return PartType.HEAD;

        if(offset.getY() > -LEG_OFFSET) // Legs
        {
            if (face == BlockFace.NORTH && offset.getX() > 0) return PartType.LEG_LEFT;
            else if (face == BlockFace.NORTH && offset.getX() < 0) return PartType.LEG_RIGHT;
            else if (face == BlockFace.SOUTH && offset.getX() < 0) return PartType.LEG_LEFT;
            else if (face == BlockFace.SOUTH && offset.getX() > 0) return PartType.LEG_RIGHT;

            else if (face == BlockFace.EAST && offset.getZ() < 0) return PartType.LEG_LEFT;
            else if (face == BlockFace.EAST && offset.getZ() > 0) return PartType.LEG_RIGHT;
            else if (face == BlockFace.WEST && offset.getZ() < 0) return PartType.LEG_LEFT;
            else if (face == BlockFace.WEST && offset.getZ() > 0) return PartType.LEG_RIGHT;
        }
        // Body
        return PartType.BODY;
    }

    private static Side GetSide(final Entity ent0, final Entity ent1)
    {
        double yawDiff = GetYawDifference(ent0, ent1);
        if (yawDiff > 67.5 && yawDiff < 67.5 + 45)
            return Side.LEFT;
        else if (yawDiff >= 67.5 + 45 && yawDiff <= 67.5 + 45 + 135)
            return Side.BACK;
        else if (yawDiff > 67.5 + 45 + 135 && yawDiff < 67.5 + 45 + 135 + 45)
            return Side.RIGHT;
        return Side.FRONT;
    }

    private static double GetYawDifference(final Entity ent0, final Entity ent1)
    {
        return Math.abs(ent1.getLocation().getYaw() - ent0.getLocation().getYaw());
    }

    public enum Side
    {
        FRONT,
        BACK,
        LEFT,
        RIGHT
    }
}
