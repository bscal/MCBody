package me.bscal.mcbody.entities.profiles;

import java.util.Collections;
import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.bscal.mcbody.body.PartType;

public abstract class MobProfile
{
    public final double headMultiplier;
    public final double bodyMultiplier;
    public final double legMultiplier;
    public final double armMultiplier;
    public final double specialMultiplier;

    public final List<Class<?>> validEntities;

    protected MobProfile(final double headAmp, final double bodyAmp, final double legAmp, final double armAmp,
            final double specAmp, final List<Class<?>> entities)
    {
        headMultiplier = headAmp;
        bodyMultiplier = bodyAmp;
        legMultiplier = legAmp;
        armMultiplier = armAmp;
        specialMultiplier = specAmp;
        validEntities = Collections.unmodifiableList(entities);
    }

    public abstract double OnDamageHead(EntityDamageByEntityEvent evt, DamageCause cause, double dmg, PartType type);

    public abstract double OnDamageBody(EntityDamageByEntityEvent evt, DamageCause cause, double dmg, PartType type);

    public abstract double OnDamageArm(EntityDamageByEntityEvent evt, DamageCause cause, double dmg, PartType type);

    public abstract double OnDamageLeg(EntityDamageByEntityEvent evt, DamageCause cause, double dmg, PartType type);

    public abstract double OnDamageSpecial(EntityDamageByEntityEvent evt, DamageCause cause, double dmg, PartType type);

    public boolean IsValid(final EntityDamageByEntityEvent evt)
    {
        Entity damagee = evt.getEntity();
        for (int i = 0; i < validEntities.size(); i++)
        {
            if (validEntities.get(i).isInstance(damagee))
                return true;
        }
        return false;
    }

}
