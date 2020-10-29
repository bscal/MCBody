package me.bscal.mcbody.healing;

import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EntityTreatmentEvent extends Event implements Cancellable {

    // Public

    public EntityTreatmentEvent(Entity entity, Entity healer, int type, int bodyPart) {
        this.m_entity = entity;
        this.m_healer = healer;
        this.m_type = type;
        this.m_bodyPart = bodyPart;
    }

    public Entity GetEntity() {
        return m_entity;
    }

    public Entity GetHealer() {
        return m_healer;
    }

    public int GetType() {
        return m_type;
    }

    public int GetBodyPart() {
        return m_bodyPart;
    }

    public boolean isCancelled() {
        return this.m_isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.m_isCancelled = isCancelled;
    }

    // Private

    private final Entity m_entity;
    private final Entity m_healer;
    private final int m_type;
    private final int m_bodyPart;

    private boolean m_isCancelled;

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}
