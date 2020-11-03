package me.bscal.mcbody.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.bscal.mcbody.body.PartType;
import me.bscal.mcbody.entities.CombatData;

public class MCBodyEntityDamageByEntityEvent extends Event
{

    public MCBodyEntityDamageByEntityEvent(EntityDamageByEntityEvent evt, PartType type, CombatData data)
    {
        m_evt = evt;
        m_type = type;
        m_data = data;
    }

    public boolean isCancelled()
    {
        return this.m_isCancelled;
    }

    public void setCancelled(boolean isCancelled)
    {
        this.m_isCancelled = isCancelled;
    }

    public EntityDamageByEntityEvent GetEvent()
    {
        return m_evt;
    }

    public PartType GetType()
    {
        return m_type;
    }

    public CombatData GetCombatData()
    {
        return m_data;
    }

    public void SetType(PartType type)
    {
        m_type = type;
    }

    public void SetCombatData(CombatData data)
    {
        m_data = data;
    }

    // Private

    private EntityDamageByEntityEvent m_evt;
    private PartType m_type;
    private CombatData m_data;

    private boolean m_isCancelled;

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers()
    {
        return HANDLERS;
    }

    public static HandlerList getHandlerList()
    {
        return HANDLERS;
    }


}
