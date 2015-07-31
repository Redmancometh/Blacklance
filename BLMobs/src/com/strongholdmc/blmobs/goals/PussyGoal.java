package com.strongholdmc.blmobs.goals;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import net.citizensnpcs.api.astar.pathfinder.MinecraftBlockExaminer;
import net.citizensnpcs.api.npc.NPC;

public class PussyGoal extends BehaviorGoalAdapter
{
    public static Random random = new Random();
    private NPC npc;
    private int xRange;
    private Location spawnLoc;
    private boolean forceFinish;

    public PussyGoal(NPC npc, int xRange, Location spawnLoc)
    {
	this.spawnLoc = spawnLoc;
	this.npc = npc;
	this.xRange = xRange;
    }

    @Override
    public void reset()
    {
	forceFinish = false;
    }

    @EventHandler
    public void onFinish(NavigationCompleteEvent event)
    {
	forceFinish = true;
    }

    
    @Override
    public BehaviorStatus run()
    {
	if (!npc.getNavigator().isNavigating() || forceFinish)
	{
	    return BehaviorStatus.SUCCESS;
	}
	return BehaviorStatus.RUNNING;
    }

    @Override
    public boolean shouldExecute()
    {
	if(!npc.isSpawned() || npc.getNavigator().isNavigating())
	{
	    return false;
	}
	Location dest = findPanicPosition();
	npc.getNavigator().setTarget(dest);
	return true;
    }

    private Location findPanicPosition()
    {
	Location base = this.npc.getEntity().getLocation().getBlock().getLocation();
	Location found = null;
	for (int i = 0; i < 5; i++)
	{
	    int x = base.getBlockX() + random.nextInt(2 * xRange) - xRange;
	    int z = base.getBlockZ() + random.nextInt(2 * xRange) - xRange;
	    int y = base.getBlockY() + random.nextInt(2 * 1) - 1;
	    Block block = base.getWorld().getBlockAt(x, y, z);
	    if (MinecraftBlockExaminer.canStandOn(block)&&isProperPosition(block.getLocation()))
	    {
		found = block.getLocation();
		if(!canTraverse(base, found)){continue;}
		break;
	    }
	}
	return found;
    }

    public boolean canTraverse(Location base, Location found)
    {
	BlockIterator bi = new BlockIterator(base.getWorld(), base.toVector(), new Vector(found.getBlockX()-base.getBlockX(),found.getBlockY()-base.getBlockY(), found.getBlockZ()-base.getBlockZ()), 0, (int) Math.floor(base.distanceSquared(found)));
	boolean canTraverse = true;
	while(bi.hasNext())
	{
	    switch(bi.next().getType())
	    {
	    case AIR:
		break;
	    case WEB:
		break;
	    default: canTraverse = false;
	    }
	}
	return canTraverse;
    }
    
    public static PussyGoal createWithNPCAndRange(NPC npc, int xrange, Location spawn)
    {
	return new PussyGoal(npc, xrange, spawn);
    }

    
    public boolean isProperPosition(Location to)
    {
	double xDiff = to.getX() - spawnLoc.getX();
	double zDiff = to.getZ() - spawnLoc.getZ();
	double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
	if(distanceXZ<xRange)
	{
	    return true;
	}
	return false;
    }
}
