package me.bscal.mcbody.entities;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import gyurix.configfile.ConfigData;
import me.bscal.mcbody.MCBody;
import me.bscal.mcbody.body.BodyPart;
import me.bscal.mcbody.body.PartType;
import me.bscal.mcbody.events.MCBodyPartDamageEvent;

public class BodyPlayer
{

    private final Map<Integer, BodyPart> m_bodyParts = new HashMap<>();
    private final Map<BodyPart, int[]> m_partModifiers = new HashMap<>();
    private Player m_player;

    public BodyPlayer(Player p)
    {
        m_player = p;

        AddPart(new BodyPart(PartType.HEAD.id, 6.0));
        AddPart(new BodyPart(PartType.BODY.id, 14.0));

        AddPart(new BodyPart(PartType.LEG_LEFT.id, 10.0));
        AddPart(new BodyPart(PartType.LEG_RIGHT.id, 10.0));

        AddPart(new BodyPart(PartType.ARM_LEFT.id, 8.0));
        AddPart(new BodyPart(PartType.ARM_RIGHT.id, 8.0));
    }

    public void HandleDamage(EntityDamageByEntityEvent e, PartType type, CombatData data)
    {
        if (type == null || !m_bodyParts.containsKey(type.id))
            MCBody.PrintErr("PartType is null or BodyPlayer does not contain part id. " + type, false);

        BodyPart bodyPart = m_bodyParts.get(type.id);
        if (bodyPart == null) return;

        MCBodyPartDamageEvent evt = new MCBodyPartDamageEvent(e, type, data, bodyPart);
        Bukkit.getPluginManager().callEvent(evt);

        if (evt.isCancelled()) return;

        if (m_partModifiers.containsKey(bodyPart))
        {
            for (int id : m_partModifiers.get(bodyPart))
            {
                MCBody.Print(id);
            }
        }
    }

    public void AddPart(BodyPart part)
    {
        m_bodyParts.put(part.GetID(), part);
    }

    public void SetHP(PartType type, double hp)
    {
        m_bodyParts.get(type.id).SetHP(hp);
    }

    public void AddHP(PartType type, double hp)
    {
        m_bodyParts.get(type.id).AddHP(hp);
    }

    public void Heal(PartType type)
    {
        m_bodyParts.get(type.id).Heal();
    }

    public void HealAll()
    {
        m_bodyParts.forEach((id, part) -> part.Heal());
    }

    public BodyPart GetPart(int id)
    {
        if (!m_bodyParts.containsKey(id))
        {
            MCBody.Logger.severe(MessageFormat.format("BodyPart map does not contain id: {0}.", id));
            return null;
        }

        return m_bodyParts.get(id);
    }

    public BodyPart GetPartByName(String name)
    {
        if (name == null || PartType.valueOf(name.toUpperCase()) == null)
        {
            MCBody.Logger.severe(MessageFormat.format("PartType does not contain type: {0}.", name));
            return null;
        }

        return m_bodyParts.get(PartType.valueOf(name.toUpperCase()).id);
    }

    public Map<Integer, BodyPart> GetParts()
    {
        return m_bodyParts;
    }

    public Player GetPlayer()
    {
        return m_player;
    }

    public void Serialize()
    {
        for (BodyPart part : m_bodyParts.values())
        {
            MCBody.Get().GetUsersFile().setData(m_player.getUniqueId().toString() + "." + part.GetID(),
                    ConfigData.serializeObject(part));
        }
    }

    public void Deserialize()
    {
        for (String str : MCBody.Get().GetUsersFile().getStringKeyList(m_player.getUniqueId().toString()))
        {
            BodyPart part = MCBody.Get().GetUsersFile().getData(str).deserialize(BodyPart.class);
            AddPart(part);
        }
    }

}
