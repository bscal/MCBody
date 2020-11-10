package me.bscal.mcbody.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import me.DevTec.TheAPI.Utils.DataKeeper.User;
import me.bscal.mcbody.MCBody;
import me.bscal.mcbody.entities.BodyPlayer;

public class PlayerListeners implements Listener
{

    @EventHandler
    public void OnJoin(PlayerJoinEvent e)
    {
        User user = new User(e.getPlayer());
        BodyPlayer body = new BodyPlayer(e.getPlayer());
        if (user.data().isKey("bodyparts"))
        {
            body.Deserialize(user);
        }
        MCBody.Get().GetPlayerPartManager().AddPlayer(e.getPlayer(), body);
    }

    @EventHandler
    public void OnExit(PlayerQuitEvent e)
    {
        User user = new User(e.getPlayer());
        BodyPlayer p = MCBody.Get().GetPlayerPartManager().GetPlayer(e.getPlayer());
        p.Serialize(user);
        user.save();
    }

    @EventHandler
    public void OnRespawn(PlayerRespawnEvent e)
    {
        BodyPlayer p = MCBody.Get().GetPlayerPartManager().GetPlayer(e.getPlayer());
        p.HealAll();
    }

}
