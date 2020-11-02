package me.bscal.mcbody;

import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import gyurix.configfile.ConfigFile;
import gyurix.mysql.MySQLDatabase;
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

    private static String CONSOLE_PREFIX = ChatColor.DARK_RED + "[MCBody]: ";
    private static final ChatColor CONSOLE_INFO = ChatColor.AQUA;
    private static final ChatColor CONSOLE_ERR = ChatColor.RED;

    private ConfigFile m_config;
    private ConfigFile m_userData;

    private EntityManager m_eMgr;
    private PlayerPartManager m_ppMgr;

    @Override
    public void onEnable()
    {
        m_singleton = this;
        Logger = getLogger();
        CONSOLE_PREFIX = MessageFormat.format("{0}[{1}{2}{0}] ",
            ChatColor.GOLD, ChatColor.YELLOW, getName());

        saveDefaultConfig();

        m_config = new ConfigFile(new File(getDataFolder() + File.separator + "config.yml"));

        if(m_config.getBoolean("useMYSQL"))
        {
            m_userData = new ConfigFile(GetDatabase());
        }
        else m_userData = new ConfigFile(new File(getDataFolder() + File.separator + "user_data.yml"));
        Debug = m_config.getBoolean("debug");

        PluginManager pluginManager = getServer().getPluginManager();

        Print(MessageFormat.format("======= MCBody starting (Debug: {0})... =======", Debug));

        m_eMgr = new EntityManager();
        pluginManager.registerEvents(new CombatListeners(), this);
        Print("EntityManager started.");

        if(m_config.getBoolean("individualPlayerParts"))
        {
            // Enables health for individual body parts.
            m_ppMgr = new PlayerPartManager();
            pluginManager.registerEvents(m_ppMgr, this);
            Print("PlayerPartManager started.");
        }

        Print("======= MCBody started! =======");
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
        if(disable) m_singleton.getServer().getPluginManager().disablePlugin(m_singleton);
    }

    public ConfigFile GetConfigFile()
    {
        return m_config;
    }

    public ConfigFile GetUsersFile()
    {
        return m_userData;
    }

    public EntityManager GetEntityManager()
    {
        return m_eMgr;
    }

    public PlayerPartManager GetPlayerPartManager()
    {
        return m_ppMgr;
    }

    private MySQLDatabase GetDatabase()
    {
        MySQLDatabase db = new MySQLDatabase(m_config.getString("database.host"),
            m_config.getString("database.database"),
            m_config.getString("database.user"),
            m_config.getString("database.password"));

        db.table = getName();
        return db;
    }

}
