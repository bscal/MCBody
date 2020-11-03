package me.bscal.mcbody.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.loisduplain.api.raycast.*;
import fr.loisduplain.api.raycast.Raycast.RaycastType;
import me.bscal.mcbody.MCBody;
import me.bscal.mcbody.body.PartType;
import me.bscal.mcbody.entities.CombatData;

public class CombatListeners implements Listener
{

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
        LivingEntity damager = (LivingEntity) e.getDamager();
        LivingEntity damagee = (LivingEntity) e.getEntity();
        DamageCause cause = e.getCause();
        double damage = e.getDamage();

        m_data.side = GetSide(damagee, damager);
        m_data.yaw = GetYaw(damagee);

        m_data.distance = damager.getLocation().distance(damagee.getLocation());
        Location loc = damager.getEyeLocation();
        m_data.ray = new Raycast(loc, m_data.distance + RAY_LENGTH);
        m_data.ray.setShowRayCast(MCBody.Debug);
        if (m_data.ray.compute(RaycastType.ENTITY))
        {
            // Hit
            Location hitLoc = m_data.ray.getHurtLocation();
            Location offset = damagee.getLocation().subtract(hitLoc);
            PartType type = GetPartTypeFromLoc(offset, m_data.yaw);
            if (MCBody.Debug)
                MCBody.PrintFormat("CombatListener", cause, damage, type, m_data.yaw, m_data.side, offset);

            if (MCBody.Get().GetEntityManager().IsActive())
                MCBody.Get().GetEntityManager().HandleDamage(e, cause, damage, type, m_data);
        }
    }

    private PartType GetPartTypeFromLoc(final Location offset, final Yaw yaw)
    {
        // heights .4 .8 .6 , arms .6 / width .8 , arms .4
        if(offset.getY() < -HEAD_OFFSET) // Head shot
            return PartType.HEAD;

        if(offset.getY() > -LEG_OFFSET) // Legs
        {
            if (yaw == Yaw.NORTH && offset.getX() > 0) return PartType.LEG_LEFT;
            else if (yaw == Yaw.NORTH && offset.getX() < 0) return PartType.LEG_RIGHT;
            else if (yaw == Yaw.SOUTH && offset.getX() < 0) return PartType.LEG_LEFT;
            else if (yaw == Yaw.SOUTH && offset.getX() > 0) return PartType.LEG_RIGHT;

            else if (yaw == Yaw.EAST && offset.getZ() < 0) return PartType.LEG_LEFT;
            else if (yaw == Yaw.EAST && offset.getZ() > 0) return PartType.LEG_RIGHT;
            else if (yaw == Yaw.WEST && offset.getZ() < 0) return PartType.LEG_LEFT;
            else if (yaw == Yaw.WEST && offset.getZ() > 0) return PartType.LEG_RIGHT;
        }
        // Body
        return PartType.BODY;
    }

    private static Side GetSide(final Entity ent0, final Entity ent1)
    {
        double yawDiff = GetYawDifference(ent0, ent1);
        if (yawDiff > 67.5 || yawDiff < 67.5 + 45)
            return Side.LEFT;
        else if (yawDiff >= 67.5 + 45 || yawDiff <= 67.5 + 45 + 135)
            return Side.BACK;
        else if (yawDiff > 67.5 + 45 + 135 || yawDiff < 67.5 + 45 + 135 + 45)
            return Side.RIGHT;
        return Side.FRONT;
    }

    private static double GetYawDifference(final Entity ent0, final Entity ent1)
    {
        return Math.abs(ent1.getLocation().getYaw() - ent0.getLocation().getYaw());
    }

    private static Yaw GetYaw(final Entity ent)
    {
        float yaw = ent.getLocation().getYaw();
        yaw = (yaw % 360 + 360) % 360; // true modulo, as javas modulo is weird for negative values
        if(yaw > 135 || yaw < -135)
        {
            return Yaw.NORTH;
        }
        else if(yaw < -45)
        {
            return Yaw.EAST;
        }
        else if(yaw > 45)
        {
            return Yaw.WEST;
        }
        else
        {
            return Yaw.SOUTH;
        }
    }

    public enum Side
    {
        FRONT,
        BACK,
        LEFT,
        RIGHT
    }

    public enum Yaw
    {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
}
