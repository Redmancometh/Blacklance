package com.strongholdmc.blmobs.main;
import java.io.File;

import net.citizensnpcs.api.CitizensAPI;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.strongholdmc.blmobs.config.ConfigUtil;
import com.strongholdmc.blmobs.listeners.MobDeath;
import com.strongholdmc.blmobs.listeners.MobSpawnListener;
import com.strongholdmc.blmobs.listeners.PathingListeners;
import com.strongholdmc.blmobs.listeners.PickupListener;
import com.strongholdmc.blmobs.listeners.TargetListener;
import com.strongholdmc.blmobs.trait.blm;
public class Main extends JavaPlugin
{
    FileConfiguration zones;
    FileConfiguration drops;
    ConfigUtil confProc;

    public void onEnable()
    {
	File zoneFile = new File(this.getDataFolder(), "mobzones.yml");
	File dropFile = new File(this.getDataFolder(), "drops.yml");
	if (!zoneFile.exists())
	{
	    this.saveResource("mobzones.yml", false);
	}
	if (!dropFile.exists())
	{
	    this.saveResource("drops.yml", false);
	}
	zones = YamlConfiguration.loadConfiguration(zoneFile);
	drops = YamlConfiguration.loadConfiguration(dropFile);
	net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(blm.class).withName("blm"));
	Bukkit.getPluginManager().registerEvents(new PickupListener(), this);
	Bukkit.getPluginManager().registerEvents(new PathingListeners(), this);
	Bukkit.getPluginManager().registerEvents(new MobDeath(), this);
	Bukkit.getPluginManager().registerEvents(new TargetListener(), this);
	confProc = new ConfigUtil(zones, drops);
	confProc.getAllRegions();
	confProc.setAllRegions();
	Bukkit.getPluginManager().registerEvents(new MobSpawnListener(confProc), this);
    }

    public void onDisable()
    {
	CitizensAPI.getNPCRegistry().deregisterAll();
    }

    public FileConfiguration getZones()
    {
	return zones;
    }

    public FileConfiguration getDrops()
    {
	return drops;
    }

}
