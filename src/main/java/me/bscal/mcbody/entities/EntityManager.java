package me.bscal.mcbody.entities;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import me.bscal.mcbody.MCBody;
import me.bscal.mcbody.body.PartType;
import me.bscal.mcbody.entities.profiles.MobProfile;
import me.bscal.mcbody.entities.profiles.PlayerProfile;

public class EntityManager
{

    private final List<MobProfile> m_profiles = new ArrayList<>();

    private boolean m_isActive;

    public EntityManager()
    {
        // IDEA If we need to load any profiles from file

        m_profiles.add(new PlayerProfile());
    }

    public void HandleDamage(EntityDamageByEntityEvent evt, DamageCause cause, double dmg, PartType type)
    {
        if (evt == null || type == null)
        {
            MCBody.PrintErr((MessageFormat
                    .format("Parameter for EntityManager::HandleDamage was null.\nEvent: {0} \nType: {1}", evt, type)),
                    true);
            return;
        }

        // Search for a valid profile based on kind of entity
        MobProfile profile = FindProfile(evt);

        if (profile == null)
        {
            MCBody.PrintErr("MobProfile in EntityManager was null.", false);
            return;
        }

        // Call the correct function based on part.
        if (type == PartType.BODY)
            profile.OnDamageBody(evt, cause, dmg, type);
        else
            if (type == PartType.HEAD)
                profile.OnDamageHead(evt, cause, dmg, type);
            else
                if (type == PartType.LEG_LEFT || type == PartType.LEG_RIGHT)
                    profile.OnDamageLeg(evt, cause, dmg, type);
                else
                    if (type == PartType.ARM_LEFT || type == PartType.ARM_RIGHT)
                        profile.OnDamageArm(evt, cause, dmg, type);

    }

    public MobProfile FindProfile(final EntityDamageByEntityEvent evt)
    {
        for (int i = 0; i < m_profiles.size(); i++)
        {
            if (m_profiles.get(i).IsValid(evt))
                return m_profiles.get(i);
        }
        return null;
    }

    public void SetActive(boolean active)
    {
        m_isActive = active;
    }

    public boolean GetActive()
    {
        return m_isActive;
    }

}
