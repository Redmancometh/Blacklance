package com.strongholdmc.blresources.main;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.strongholdmc.blresources.listeners.CommandExec;
import com.strongholdmc.blresources.listeners.HarvestListeners;
import com.strongholdmc.blresources.listeners.MiningListeners;
import com.strongholdmc.blresources.util.ConfigProcessor;

public class Main extends JavaPlugin
{
    static FileConfiguration nodes;
    static File nodeConf;
    ConfigProcessor confProc;
    public void onEnable()
    {
	nodeConf = new File(this.getDataFolder(), "nodes.yml");
	if(!nodeConf.exists()){this.saveResource("nodes.yml", false);}
	nodes = YamlConfiguration.loadConfiguration(nodeConf);
	confProc = new ConfigProcessor(nodes);
	PluginManager pm = Bukkit.getPluginManager();
	pm.registerEvents(new HarvestListeners(), this);
	pm.registerEvents(new MiningListeners(), this);
	CommandExec exec = new CommandExec(confProc);
	this.getCommand("addnode").setExecutor(exec);
	confProc.setNodeLists();
    }
    public FileConfiguration getNodes()
    {
	return nodes;
    }
    public ConfigProcessor getConfProc()
    {
	return confProc;
    }
    public static void saveNodes()
    {
	try
	{
	    nodes.save(nodeConf);
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
    }
}
