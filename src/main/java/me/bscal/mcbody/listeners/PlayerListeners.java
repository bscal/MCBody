package me.bscal.mcbody.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import gyurix.configfile.ConfigData;
import me.bscal.mcbody.MCBody;
import me.bscal.mcbody.entities.BodyPlayer;

public class PlayerListeners implements Listener
{

    @EventHandler
    public void OnJoin(PlayerJoinEvent e)
    {
        ConfigData data = MCBody.Get().GetUsersFile().getData(e.getPlayer().getUniqueId().toString());
        BodyPlayer body = new BodyPlayer(e.getPlayer());
        if (!data.isEmpty())
        {
            body.Deserialize();
        }
        MCBody.Get().GetPlayerPartManager().AddPlayer(e.getPlayer(), body);
    }

    @EventHandler
    public void OnExit(PlayerQuitEvent e)
    {
        BodyPlayer p = MCBody.Get().GetPlayerPartManager().GetPlayer(e.getPlayer());
        p.Serialize();
        MCBody.Get().GetUsersFile().save();
        MCBody.Get().GetPlayerPartManager().RemovePlayer(e.getPlayer());
    }

    @EventHandler
    public void OnRespawn(PlayerRespawnEvent e)
    {
        BodyPlayer p = MCBody.Get().GetPlayerPartManager().GetPlayer(e.getPlayer());
        p.HealAll();
    }

}
