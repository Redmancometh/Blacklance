package com.strongholdmc.blevents.resourcelisteners;

import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blevents.resourceevents.MiningEvent;

public class MiningListener implements Listener
{
    @EventHandler
    public void onMine(BlockBreakEvent e)
    {
	e.setCancelled(true);
	new BukkitRunnable()
	{
	    public void run()
	    {
		try
		{
		    Material m = e.getBlock().getType();
		    if (isOre(m).get())
		    {
			Bukkit.getPluginManager().callEvent(new MiningEvent(e.getPlayer(), e.getBlock(), getTimeToRegen(m).get()));
		    }
		}
		catch (Throwable t)
		{
		    t.printStackTrace();
		}
	    }
	}.runTaskAsynchronously(BlackLance.getPlugin());
    }

    public CompletableFuture<Integer> getTimeToRegen(Material m)
    {
	CompletableFuture<Integer> futureValue = CompletableFuture.supplyAsync(() -> {
	    switch (m)
	    {
	    case IRON_ORE:
		return 440;
	    case COAL_ORE:
		return 440;
	    case GOLD_ORE:
		return 350;
	    case DIAMOND_ORE:
		return 1150;
	    case REDSTONE_ORE:
		return 860;
	    case LAPIS_ORE:
		return 750;
	    case EMERALD_ORE:
		return 950;
	    default:
		return 0;
	    }
	});
	return futureValue;

    }

    public CompletableFuture<Boolean> isOre(Material m)
    {
	CompletableFuture<Boolean> futureValue = CompletableFuture.supplyAsync(() -> {
	    switch (m)
	    {
	    case IRON_ORE:
		return true;
	    case COAL_ORE:
		return true;
	    case GOLD_ORE:
		return true;
	    case DIAMOND_ORE:
		return true;
	    case REDSTONE_ORE:
		return true;
	    case LAPIS_ORE:
		return true;
	    case EMERALD_ORE:
		return true;
	    default:
		return false;
	    }
	});
	return futureValue;
    }
}
