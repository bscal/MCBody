package me.bscal.mcbody.listeners;

import java.text.MessageFormat;

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

public class CombatListeners implements Listener
{

    private static final double RAY_LENGTH = 0.2;

    private static final double HEAD_OFFSET = 1.5;
    private static final double LEG_OFFSET = 0.8;

    @EventHandler
    public void OnEntityDamageByEntityEvent(EntityDamageByEntityEvent e)
    {
        LivingEntity damager = (LivingEntity) e.getDamager();
        LivingEntity damagee = (LivingEntity) e.getEntity();
        DamageCause cause = e.getCause();
        double damage = e.getDamage();

        double dist = damager.getLocation().distance(damagee.getLocation());
        Location loc = damager.getEyeLocation();
        Raycast ray = new Raycast(loc, dist + RAY_LENGTH);
        ray.setShowRayCast(MCBody.Debug);

        if(ray.compute(RaycastType.ENTITY))
        {
            // Hit
            Location hitLoc = ray.getHurtLocation();
            Location offset = damagee.getLocation().subtract(hitLoc);
            PartType type = GetPartTypeFromLoc(damagee, offset);
            MCBody.Print(MessageFormat.format("RayHit: {0}, {1}, {2}. \nOffset: {3}", cause, damage, type, offset));
            MCBody.Get().GetEntityManager().HandleDamage(e, cause, damage, type);
        }
    }

    private PartType GetPartTypeFromLoc(Entity damagee, final Location offset)
    {
        // heights .4 .8 .6 , arms .6 / width .8 , arms .4
        if(offset.getY() < -HEAD_OFFSET) // Head shot
            return PartType.HEAD;

        Yaw yaw = GetYaw(damagee);
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



    private static Yaw GetYaw(Entity ent)
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

    public enum Yaw
    {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
}
