package me.bscal.mcbody.player;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import me.bscal.mcbody.MCBody;
import me.bscal.mcbody.body.BodyPart;
import me.bscal.mcbody.body.PartType;

public class BodyPlayer
{

    private final Map<Integer, BodyPart> m_bodyParts = new HashMap<>();

    public BodyPlayer()
    {
        AddPart(new BodyPart(PartType.));
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

}
