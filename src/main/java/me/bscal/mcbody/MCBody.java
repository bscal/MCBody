package me.bscal.mcbody;

import org.bukkit.entity.Player;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class MCBody extends JavaPlugin
{

    public static Logger Logger;

    private static MCBody Singleton;

    @Override
    public void onEnable()
    {
        Singleton = this;

        Logger = getLogger();

        Player p;
    }

    public static MCBody Get()
    {
        return Singleton;
    }

}
