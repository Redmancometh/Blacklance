package com.strongholdmc.blresources.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blevents.resourceevents.MiningEvent;

public class MiningListeners implements Listener
{
    @EventHandler
    public void onMining(MiningEvent e)
    {
	new BukkitRunnable()
	{
	    public void run()
	    {
		e.getPlayer().getInventory().addItem(new ItemStack(e.getBlock().getType()));
		Bukkit.broadcastMessage(e.getRegenTime()+" ");
		scheduleRegen(e.getBlock(), e.getRegenTime());
	    }
	}.runTask(BlackLance.getPlugin());
    }
    public void scheduleRegen(Block b, int delay)
    {
	Bukkit.broadcastMessage("TEST "+delay);
	Material m = b.getType();
	b.setType(Material.STONE);
	new BukkitRunnable()
	{
	    public void run()
	    {
		b.setType(m);
	    }
	}.runTaskLater(BlackLance.getPlugin(), delay);
    }
}
