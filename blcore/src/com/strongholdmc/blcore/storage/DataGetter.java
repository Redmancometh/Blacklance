package com.strongholdmc.blcore.storage;

import java.io.IOException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DataGetter
{
    // Configuration Section!
    private FileConfiguration config;

    public DataGetter(JavaPlugin blacklance)
    {
	this.config = blacklance.getConfig();
    }

    public boolean PlayerExists(Player p) throws IOException
    {
	if (config.isSet("Quests." + p.getName()))
	{
	    return true;
	}
	else
	{
	    return false;
	}
    }

    public boolean checkInProgress(int questID, Player p)
    {
	if (config.isSet("Quests." + p.getName() + ".QIP." + questID))
	{
	    return true;
	}
	else
	{
	    return false;
	}
    }

    public boolean checkCompleted(int questID, Player p)
    {
	if (config.isSet("Quests." + p.getName() + ".QC." + questID))
	{
	    return true;
	}
	else
	{
	    return false;
	}
    }
}
