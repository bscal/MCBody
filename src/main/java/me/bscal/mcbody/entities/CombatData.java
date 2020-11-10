package me.bscal.mcbody.entities;

import org.bukkit.block.BlockFace;

import fr.loisduplain.api.raycast.Raycast;
import me.bscal.mcbody.listeners.CombatListeners.Side;

public class CombatData {

    public Side side;
    public BlockFace face;
    public double distance;
    public Raycast ray;

    public CombatData() {}

    public CombatData(final Side side, final BlockFace face, final double distance, final Raycast ray)
    {
        this.side = side;
        this.face = face;
        this.distance = distance;
        this.ray = ray;
    }

}
