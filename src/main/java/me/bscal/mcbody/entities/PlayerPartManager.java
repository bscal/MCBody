package me.bscal.mcbody.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PlayerPartManager implements Listener
{
    private final Map<UUID, BodyPlayer> m_players = new HashMap<>();

    public void AddPlayer(final Player p, final BodyPlayer body)
    {
        if (p == null || body == null)
            return;

        m_players.put(p.getUniqueId(), body);
    }

    public BodyPlayer GetPlayer(final Player p)
    {
        return m_players.get(p.getUniqueId());
    }

    public Map<UUID, BodyPlayer> GetPlayerMap()
    {
        return m_players;
    }

    public void RemovePlayer(final Player player)
    {
        if (m_players.containsKey(player.getUniqueId()))
        {
            m_players.put(player.getUniqueId(), null);
            m_players.remove(player.getUniqueId());
        }
    }

    public boolean ContainsPlayer(final Player player)
    {
        return m_players.containsKey(player.getUniqueId());
    }

}
