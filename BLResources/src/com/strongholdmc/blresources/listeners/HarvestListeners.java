package com.strongholdmc.blresources.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blevents.resourceevents.HarvestHayEvent;
import com.strongholdmc.blevents.resourceevents.HarvestLogEvent;

public class HarvestListeners implements Listener
{
    @EventHandler
    public void harvestHay(HarvestHayEvent e)
    {
	new BukkitRunnable()
	{
	    public void run()
	    {
		e.getPlayer().getInventory().addItem(new ItemStack(Material.HAY_BLOCK));
	    }
	}.runTask(BlackLance.getPlugin());
    }
    public void harvestLog(HarvestLogEvent e)
    {
	new BukkitRunnable()
	{
	    public void run()
	    {
		e.getPlayer().getInventory().addItem(new ItemStack(Material.LOG));
	    }
	}.runTask(BlackLance.getPlugin());
    }
}
