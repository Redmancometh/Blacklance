package com.strongholdmc.blevents.listeners;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blevents.MerchantEvents.MerchantBuyEvent;
import com.strongholdmc.blevents.MerchantEvents.MerchantSellEvent;

public class MerchantListener implements Listener
{
    @EventHandler
    public void onBuyItem(InventoryClickEvent e)
    {
	if (e.getInventory().getTitle().contains("§p§o§o§p§t§a§r§d"))
	{
	    e.setCancelled(true);
	    if (e.getCurrentItem() == null || e.getCurrentItem().getType()==Material.AIR)
	    {
		return;
	    }
	    if (!(e.getClickedInventory()==e.getWhoClicked().getInventory()))
	    {
		new BukkitRunnable()
		{
		    public void run()
		    {
			Bukkit.getPluginManager().callEvent(new MerchantBuyEvent((Player) e.getWhoClicked(), e.getCurrentItem()));
		    }
		}.runTaskAsynchronously(BlackLance.getPlugin());
	    }
	    else
	    {
		new BukkitRunnable()
		{
		    public void run()
		    {
			Bukkit.getPluginManager().callEvent(new MerchantSellEvent((Player) e.getWhoClicked(), e.getCurrentItem()));
		    }
		}.runTaskAsynchronously(BlackLance.getPlugin());
	    }
	}
    }
}
