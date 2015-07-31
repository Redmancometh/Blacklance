package com.strongholdmc.blevents.main;

import com.strongholdmc.blevents.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.strongholdmc.blevents.resourcelisteners.HarvestHay;
import com.strongholdmc.blevents.resourcelisteners.MiningListener;

public class BLEvents extends JavaPlugin
{
    public void onEnable()
    {
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new GeneralMeleeListener(), this);
		pm.registerEvents(new SpecificMeleeListener(), this);
		pm.registerEvents(new WeaponListener(), this);
		pm.registerEvents(new HarvestHay(), this);
		pm.registerEvents(new MerchantListener(), this);
		pm.registerEvents(new MiningListener(), this);
		pm.registerEvents(new RPGArmorEquipListener(), this);
    }
}
