package me.bscal.mcbody.cmds;

import org.bukkit.entity.Player;

import me.bscal.mcbody.ui.BodyPartUI;
import xyz.tozymc.spigot.api.command.Command;
import xyz.tozymc.spigot.api.command.PlayerCommand;
import xyz.tozymc.spigot.api.command.result.CommandResult;
import xyz.tozymc.spigot.api.command.result.TabResult;
import xyz.tozymc.spigot.api.util.bukkit.permission.PermissionWrapper;

public class GUICommand extends PlayerCommand
{

    public GUICommand(Command parent)
    {
        super(parent, "info", "hp");
    }

    @Override
    public PermissionWrapper getPermission()
    {
        return parent.getPermission();
    }

    @Override
    public String getSyntax()
    {
        return "/mcbody info|hp";
    }

    @Override
    public String getDescription()
    {
        return "Opens player health info.";
    }

    @Override
    public CommandResult onCommand(Player player, String[] params)
    {
        BodyPartUI.open(player);
        return CommandResult.SUCCESS;
    }

    @Override
    public TabResult onTab(Player player, String[] params)
    {
        return TabResult.of("", "info");
    }

}
