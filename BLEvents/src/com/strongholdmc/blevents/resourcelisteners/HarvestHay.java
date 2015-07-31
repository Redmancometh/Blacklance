package com.strongholdmc.blevents.resourcelisteners;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.strongholdmc.blcore.main.BlackLance;

public class HarvestHay implements Listener
{
    @EventHandler
    public void harvest(PlayerInteractEvent e) throws InterruptedException, ExecutionException
    {
	if(e.getAction()==Action.RIGHT_CLICK_BLOCK)
	{
	    e.setCancelled(true);
	}
	new BukkitRunnable()
	{
	    public void run()
	    {
		try
		{
		    if (e.getItem() != null && e.getMaterial() == Material.HAY_BLOCK)
		    {
			if (hasHoe(e.getItem().getType()).get())
			{

			}
		    }
		}
		catch (Throwable t)
		{
		    t.printStackTrace();
		}
	    }
	}.runTaskAsynchronously(BlackLance.getPlugin());
    }

    public CompletableFuture<Boolean> hasHoe(Material m)
    {
	CompletableFuture<Boolean> futureValue = CompletableFuture.supplyAsync(() -> {
	    switch (m)
	    {
	    case WOOD_HOE:
		return true;
	    case IRON_HOE:
		return true;
	    case STONE_HOE:
		return true;
	    case GOLD_HOE:
		return true;
	    case DIAMOND_HOE:
		return true;
	    default:
		return false;
	    }
	});
	return futureValue;
    }
}
