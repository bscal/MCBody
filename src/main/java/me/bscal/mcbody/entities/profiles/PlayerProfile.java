package me.bscal.mcbody.entities.profiles;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.entity.Drowned;
import org.bukkit.entity.Entity;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Piglin;
import org.bukkit.entity.Pillager;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.bscal.mcbody.body.PartType;

public class PlayerProfile extends MobProfile
{

    public PlayerProfile()
    {
        super(2.0, 1.0, 0.75, 0.75, 2.0, Arrays.asList(new Class<?>[]
        { Player.class, Zombie.class, PigZombie.class, Villager.class, Pillager.class, Piglin.class, Drowned.class
        }));
    }

    @Override
    public double OnDamageHead(EntityDamageByEntityEvent evt, DamageCause cause, double dmg, PartType type)
    {
        if (IsValid(evt))
        {
            evt.setDamage(dmg * headMultiplier);
        }
        return 0;
    }

    @Override
    public double OnDamageBody(EntityDamageByEntityEvent evt, DamageCause cause, double dmg, PartType type)
    {
        if (IsValid(evt))
        {
            evt.setDamage(dmg * bodyMultiplier);
        }
        return 0;
    }

    @Override
    public double OnDamageArm(EntityDamageByEntityEvent evt, DamageCause cause, double dmg, PartType type)
    {
        if (IsValid(evt))
        {
            evt.setDamage(dmg * armMultiplier);
        }
        return 0;
    }

    @Override
    public double OnDamageLeg(EntityDamageByEntityEvent evt, DamageCause cause, double dmg, PartType type)
    {
        if (IsValid(evt))
        {
            evt.setDamage(dmg * legMultiplier);
        }
        return 0;
    }

    @Override
    public double OnDamageSpecial(EntityDamageByEntityEvent evt, DamageCause cause, double dmg, PartType type)
    {
        if (IsValid(evt))
        {
            evt.setDamage(dmg * specialMultiplier);
        }
        return 0;
    }
}
