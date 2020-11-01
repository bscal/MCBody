package me.bscal.mcbody.listeners;

import java.text.MessageFormat;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.loisduplain.api.raycast.*;
import fr.loisduplain.api.raycast.Raycast.RaycastType;
import me.bscal.mcbody.MCBody;
import me.bscal.mcbody.body.PartType;

public class CombatListeners implements Listener
{

    private static final double RAY_LENGTH = 0.5;

    private static final double HEAD_OFFSET = 1.4;
    private static final double ARM_OFFSET = 0.4;
    private static final double LEG_OFFSET = 0.6;

    @EventHandler
    public void OnEntityDamageByEntityEvent(EntityDamageByEntityEvent e, final Entity damager, final Entity damagee,
            final DamageCause cause, final double damage)
    {
        double dist = damager.getLocation().distance(damagee.getLocation());
        Raycast ray = new Raycast(damager.getLocation(), dist + RAY_LENGTH);
        ray.setShowRayCast(MCBody.Debug);

        if (ray.compute(RaycastType.ENTITY))
        {
            // Hit
            Location hitLoc = ray.getHurtLocation();
            Location offset = damagee.getLocation().subtract(hitLoc);
            PartType type = GetPartTypeFromLoc(offset);
            MCBody.Print(MessageFormat.format("RayHit: {0}, {1}, {2}. \nOffset: {3}", cause, damage, type, offset));
            MCBody.Get().GetEntityManager().HandleDamage(e, cause, damage, type);
        }
    }

    private PartType GetPartTypeFromLoc(final Location offset)
    {
        // heights .4 .8 .6 , arms .6 / width .8 , arms .4
        if (offset.getY() > HEAD_OFFSET) // Head shot
            return PartType.HEAD;
        double side = offset.getX() - offset.getBlockZ();
        if (Math.abs(side) > ARM_OFFSET) // Arms
        {
            if (side > 0) // Left
                return PartType.ARM_LEFT;
            else // Right
                return PartType.ARM_RIGHT;
        }
        else if (offset.getY() < LEG_OFFSET) // Legs
        {
            if (side > 0) // Left
                return PartType.LEG_LEFT;
            else // Right
                return PartType.LEG_RIGHT;
        }
        else // Body
            return PartType.BODY;
    }

    private static Yaw GetYaw(Entity ent)
    {
        float yaw = ent.getLocation().getYaw();
        yaw = (yaw % 360 + 360) % 360; // true modulo, as javas modulo is weird for negative values
        if (yaw > 135 || yaw < -135)
        {
            return Yaw.NORTH;
        }
        else if (yaw < -45)
        {
            return Yaw.EAST;
        }
        else if (yaw > 45)
        {
            return Yaw.WEST;
        }
        else
        {
            return Yaw.SOUTH;
        }
    }

    public enum Yaw
    {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
}
