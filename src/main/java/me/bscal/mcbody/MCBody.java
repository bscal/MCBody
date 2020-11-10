package me.bscal.mcbody;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.DevTec.TheAPI.ConfigAPI.Config;
import me.bscal.mcbody.cmds.GUICommand;
import me.bscal.mcbody.cmds.RootCommand;
import me.bscal.mcbody.entities.EntityManager;
import me.bscal.mcbody.entities.PlayerPartManager;
import me.bscal.mcbody.listeners.CombatListeners;
import me.bscal.mcbody.listeners.PlayerListeners;
import me.bscal.mcbody.ui.BodyPartUI;
import xyz.tozymc.spigot.api.command.Command;
import xyz.tozymc.spigot.api.command.CommandController;

import java.io.File;
import java.text.MessageFormat;

public class MCBody extends JavaPlugin
{
    public static boolean Debug;
    public static ConsoleCommandSender CS;

    private static MCBody m_singleton;

    private static String CONSOLE_PREFIX;
    private static final ChatColor CONSOLE_INFO = ChatColor.AQUA;
    private static final ChatColor CONSOLE_ERR = ChatColor.RED;

    private CommandController m_cmdCtrl;
    private Config m_config;

    private EntityManager m_eMgr;
    private PlayerPartManager m_ppMgr;
    private BodyPartUI m_bodyUI;

    @Override
    public void onEnable()
    {
        m_singleton = this;
        CS = getServer().getConsoleSender();
        CONSOLE_PREFIX = MessageFormat.format("{1}[{2}{0}{1}]: ", getName(), ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE);

        saveDefaultConfig();
        m_config = new Config(getName() + File.separator + "config.yml");
        Debug = m_config.getBoolean("DebugModeEnabled");

        m_cmdCtrl = new CommandController(this);
        Command root = m_cmdCtrl.addCommand(new RootCommand());
        m_cmdCtrl.addCommand(new GUICommand(root));

        PluginManager pm = getServer().getPluginManager();

        Print(MessageFormat.format("======= MCBody starting (Debug: {0})... =======", Debug));

        m_eMgr = new EntityManager();
        m_eMgr.SetActive(MCBody.Get().GetConfigFile().getBoolean("EntityDamageEnabled"));
        pm.registerEvents(new CombatListeners(), this);
        pm.registerEvents(new PlayerListeners(), this);
        Print("EntityManager started.");

        m_ppMgr = new PlayerPartManager();
        if(m_config.getBoolean("PlayerPartsEnabled"))
        {
            // Enables health for individual body parts.
            m_ppMgr.SetActive(true);
            pm.registerEvents(m_ppMgr, this);
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
        CS.sendMessage(CONSOLE_PREFIX + CONSOLE_INFO + msg);
    }

    public static void Print(Object... msg)
    {
        CS.sendMessage(CONSOLE_PREFIX + CONSOLE_INFO + StringUtils.join(msg, ", "));
    }

    public static void PrintFormat(String title, Object... msg)
    {
        CS.sendMessage(CONSOLE_PREFIX + CONSOLE_INFO + "======= Printing " + title + " =======\n\t" + StringUtils.join(msg, ",\n\t"));
    }

    public static void PrintErr(String msg)
    {
        PrintErr(msg, false);
    }

    public static void PrintErr(String msg, boolean disable)
    {
        CS.sendMessage(CONSOLE_PREFIX + CONSOLE_ERR + msg);
        if (disable) m_singleton.getServer().getPluginManager().disablePlugin(m_singleton);
    }

    public Config GetConfigFile()
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

    public BodyPartUI getBodyPartUI()
    {
        return m_bodyUI;
    }

}
