package me.bscal.mcbody.entities;

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

}
