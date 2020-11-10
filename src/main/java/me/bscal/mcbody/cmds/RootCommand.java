package me.bscal.mcbody.cmds;

import org.bukkit.command.CommandSender;

import xyz.tozymc.spigot.api.command.CombinedCommand;
import xyz.tozymc.spigot.api.command.result.CommandResult;
import xyz.tozymc.spigot.api.command.result.TabResult;
import xyz.tozymc.spigot.api.util.bukkit.permission.PermissionWrapper;

public class RootCommand extends CombinedCommand
{

    public RootCommand()
    {
        super("mcbody");
    }

    @Override
    public String getDescription()
    {
        return "Base command for MCBody plugin.";
    }

    @Override
    public PermissionWrapper getPermission()
    {
        return PermissionWrapper.of("mcbody.basic");
    }

    @Override
    public String getSyntax()
    {
        return "/mcbody";
    }

    @Override
    public CommandResult onCommand(CommandSender sender, String[] args)
    {
        sender.sendMessage("MCBody root command.");
        return CommandResult.SUCCESS;
    }

    @Override
    public TabResult onTab(CommandSender sender, String[] args)
    {
        return TabResult.of(args[args.length-1], "info");
    }

}
