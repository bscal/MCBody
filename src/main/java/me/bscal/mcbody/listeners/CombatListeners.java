package me.bscal.mcbody.listeners;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.loisduplain.api.raycast.*;
import fr.loisduplain.api.raycast.Raycast.RaycastType;

public class CombatListeners implements Listener
{

    private static final double RAY_LENGTH = 0.5;

    private static final double HEAD_OFFSET = 1.4;

    @EventHandler
    public void OnEntityDamageByEntityEvent(EntityDamageByEntityEvent e, final Entity damager, final Entity damagee,
            final DamageCause cause, final double damage)
    {
        double dist = damager.getLocation().distance(damagee.getLocation());
        Raycast ray = new Raycast(damager.getLocation(), dist + RAY_LENGTH);

        if (ray.compute(RaycastType.ENTITY))
        {
            // Hit
            Location hitLoc = ray.getHurtLocation();
            Location offset = damagee.getLocation().subtract(hitLoc);

            // heights .4 .8 .6 , arms .6 / width .8 , arms .4

            if (offset.getY() > HEAD_OFFSET)
            {
                // HEADSHOT
            }
            // Body

            double side = offset.getX() - offset.getBlockZ();
            if (Math.abs(side) > .4) // Arms
            {
                if (side > 0)
                {
                    // Left
                }
                else
                {
                    // Right
                }
            }
            else if (offset.getY() < .6) // Legs
            {
                if (side > 0)
                {
                    // Left
                }
                else
                {
                    // Right
                }
            }
        }
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
        NORTH, EAST, SOUTH, WEST
    }
}
