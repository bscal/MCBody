package me.bscal.mcbody.entities;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import gyurix.configfile.ConfigData;
import me.bscal.mcbody.MCBody;
import me.bscal.mcbody.body.BodyPart;
import me.bscal.mcbody.body.PartType;

public class BodyPlayer
{

    private final Map<Integer, BodyPart> m_bodyParts = new HashMap<>();
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

    public void AddPart(BodyPart part)
    {
        m_bodyParts.put(part.GetID(), part);
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
