package com.strongholdmc.blevents.resourcelisteners;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blevents.resourceevents.HarvestLogEvent;

public class HarvestLog implements Listener
{
    public void onHarvest(BlockBreakEvent e)
    {
	new BukkitRunnable()
	{
	    public void run()
	    {
		Player p = e.getPlayer();
		if (p.getItemInHand() != null)
		{
		    try
		    {
			if (hasAxe(p.getItemInHand().getType()).get() && e.getBlock().getType() == Material.LOG)
			{
			    e.setCancelled(true);
			    Bukkit.getPluginManager().callEvent(new HarvestLogEvent(p, e.getBlock()));
			}
		    }
		    catch (Throwable t)
		    {
			t.printStackTrace();
		    }
		}
	    }
	}.runTaskAsynchronously(BlackLance.getPlugin());
    }

    public CompletableFuture<Boolean> hasAxe(Material m)
    {
	CompletableFuture<Boolean> futureValue = CompletableFuture.supplyAsync(() -> {
	    switch (m)
	    {
	    case WOOD_AXE:
		return true;
	    case IRON_AXE:
		return true;
	    case STONE_AXE:
		return true;
	    case GOLD_AXE:
		return true;
	    case DIAMOND_AXE:
		return true;
	    default:
		return false;
	    }
	});
	return futureValue;

    }
}
