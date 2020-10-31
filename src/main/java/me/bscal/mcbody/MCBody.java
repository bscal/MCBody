package me.bscal.mcbody;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import gyurix.configfile.ConfigFile;
import me.bscal.mcbody.entities.EntityManager;
import me.bscal.mcbody.entities.PlayerPartManager;
import me.bscal.mcbody.listeners.CombatListeners;

import java.io.File;
import java.text.MessageFormat;
import java.util.logging.Logger;

public class MCBody extends JavaPlugin
{

    public static Logger Logger;
    public static boolean Debug;

    private static MCBody m_singleton;

    private static final String CONSOLE_PREFIX = ChatColor.RED + "[MCBody]: ";
    private static final String CONSOLE_INFO = CONSOLE_PREFIX + ChatColor.YELLOW;
    private static final String CONSOLE_ERR = CONSOLE_PREFIX + ChatColor.BLUE;

    private ConfigFile m_config;
    private PluginManager m_pluginManager;
    private EntityManager m_eMgr;
    private PlayerPartManager m_ppMgr;

    @Override
    public void onEnable()
    {
        m_singleton = this;
        Logger = getLogger();

        m_config = new ConfigFile(new File(getDataFolder() + File.separator + "config.yml"));
        Debug = m_config.getBoolean("debug");

        m_pluginManager = getServer().getPluginManager();

        Print(MessageFormat.format("======= MCBody starting (Debug: {0})... =======", Debug));

        m_eMgr = new EntityManager();
        m_pluginManager.registerEvents(new CombatListeners(), this);
        Print("EntityManager started.");

        if (m_config.getBoolean("individualPlayerParts"))
        {
            // Enables health for individual body parts.
            m_ppMgr = new PlayerPartManager();
            m_pluginManager.registerEvents(m_ppMgr, this);
            Print("PlayerPartManager started.");
        }
    }

    public static MCBody Get()
    {
        return m_singleton;
    }

    public static void Print(String msg)
    {
        Logger.info(CONSOLE_INFO + msg);
    }

    public static void PrintErr(String msg, boolean disable)
    {
        Logger.severe(CONSOLE_ERR + msg);

        if (disable)
            m_singleton.getServer().getPluginManager().disablePlugin(m_singleton);
    }

    public ConfigFile GetConfigFile()
    {
        return m_config;
    }

    public EntityManager GetEntityManager()
    {
        return m_eMgr;
    }

    public PlayerPartManager GetPlayerPartManager()
    {
        return m_ppMgr;
    }

}
