package com.strongholdmc.blmobs.main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
	if(cmd.getName().equalsIgnoreCase("blr"))
	{
	    if(args.length>2)
	    {
		if(args[0].equalsIgnoreCase("region"))
		{

		}
	    }
	}
	return false;
    }
}
