package me.bscal.mcbody.entities;

import fr.loisduplain.api.raycast.Raycast;
import me.bscal.mcbody.listeners.CombatListeners.Side;
import me.bscal.mcbody.listeners.CombatListeners.Yaw;

public class CombatData {

    public Side side;
    public Yaw yaw;
    public double distance;
    public Raycast ray;

    public CombatData() {}

    public CombatData(final Side side, final Yaw yaw, final double distance, final Raycast ray)
    {
        this.side = side;
        this.yaw = yaw;
        this.distance = distance;
        this.ray = ray;
    }

}
