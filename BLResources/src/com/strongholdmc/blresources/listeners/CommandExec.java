package com.strongholdmc.blresources.listeners;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.strongholdmc.blresources.util.ConfigProcessor;

public class CommandExec implements CommandExecutor
{
    ConfigProcessor confProc;
    public CommandExec(ConfigProcessor confProc)
    {
	this.confProc=confProc;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
	Location loc1 = ((Player)sender).getLocation();
	if (cmd.getName().equalsIgnoreCase("addnode") && args.length == 2)
	{
	    Material m = Material.getMaterial(args[1]);
	    loc1 = ((Player)sender).getLocation();
	    loc1.getBlock().setType(m);
	    confProc.addResourceNode(args[0], args[1], m, loc1);
	}
	if(cmd.getName().equalsIgnoreCase("addnode") && args.length == 1)
	{
	    if(args[0].equalsIgnoreCase("hay"))
	    {
		loc1.getBlock().setType(Material.HAY_BLOCK);
		confProc.addResourceNode("Hay",null,Material.HAY_BLOCK, loc1);
	    }
	}
	return true;
    }
}
