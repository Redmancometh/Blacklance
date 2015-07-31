package com.strongholdmc.blpvp.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.strongholdmc.blpvp.listeners.CombatListeners;
import com.strongholdmc.blpvp.listeners.HologramListener;

public class Main extends JavaPlugin
{
    public void onEnable()
    {
	Bukkit.getPluginManager().registerEvents(new CombatListeners(), this);
	Bukkit.getPluginManager().registerEvents(new HologramListener(), this);
    }
}
