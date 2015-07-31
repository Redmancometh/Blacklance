package com.strongholdmc.blmobs.util;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import com.strongholdmc.blcore.main.BlackLance;
public class AsyncMobUtils
{
    public static CompletableFuture<List<Entity>> getEntitiesForAsync(Entity e, int x, int y, int z)
    {
	CompletableFuture<List<Entity>> mobList = new CompletableFuture<List<Entity>>();
	new BukkitRunnable()
	{
	    public void run()
	    {
		mobList.complete(e.getNearbyEntities(x, y, z));
	    }
	}.runTask(BlackLance.getPlugin());
	return mobList;
    }

    public static CompletableFuture<Player> getNearestPlayer(Entity e, int... range)
    {
	CompletableFuture<Player> futureValue = new CompletableFuture<Player>();
	List<Entity> eList;
	try
	{
	    eList = getEntitiesForAsync(e, range[0], range[1], range[2]).get();
	    for (Entity potentialTarget : eList)
	    {
		if (potentialTarget instanceof Player)
		{
		    futureValue.complete((Player) potentialTarget);
		}
	    }
	}
	catch (InterruptedException | ExecutionException e1)
	{
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}
	return futureValue;
    }

}
