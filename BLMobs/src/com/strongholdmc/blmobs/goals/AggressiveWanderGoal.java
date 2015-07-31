package com.strongholdmc.blmobs.goals;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.ai.tree.BehaviorGoalAdapter;
import net.citizensnpcs.api.ai.tree.BehaviorStatus;
import net.citizensnpcs.api.astar.pathfinder.MinecraftBlockExaminer;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blevents.combatevents.MobHitPlayerEvent;
import com.strongholdmc.blmobs.trait.blm;

public class AggressiveWanderGoal extends BehaviorGoalAdapter
{
    private boolean forceFinish;
    private final NPC npc;
    private final Random random = new Random();
    private final int xRange;
    private long lastAttack = 0;
    private AggressiveWanderGoal(NPC npc, int xrange)
    {
	this.npc = npc;
	this.xRange = xrange;
    }

    private Location findRandomPosition()
    {
	Location base = npc.getEntity().getLocation().getBlock().getLocation();
	Location found = null;
	for (int i = 0; i < 3; i++)
	{
	    int x = base.getBlockX() + random.nextInt(2 * xRange) - xRange;
	    int z = base.getBlockZ() + random.nextInt(2 * xRange) - xRange;
	    int y = base.getBlockY() + random.nextInt(2 * 1) - 1;
	    Block block = base.getWorld().getBlockAt(x, y, z);
	    try
	    {
		if (MinecraftBlockExaminer.canStandOn(block) && canSpawning(block.getLocation()).get())
		{
		    found = block.getLocation();
		    break;
		}
	    }
	    catch (InterruptedException | ExecutionException e)
	    {
		e.printStackTrace();
	    }
	}
	return found;
    }

    public LivingEntity findTarget(Integer Range)
    {
	if (!(this.npc.getEntity().getTicksLived() > 40))
	{
	    return null;
	}
	List<Entity> EntitiesWithinRange = this.npc.getEntity().getNearbyEntities(Range, 3, Range);
	for (Entity aTarget : EntitiesWithinRange)
	{
	    if (!(aTarget instanceof LivingEntity))
	    {
		continue;
	    }
	    else if (aTarget instanceof Player)
	    {
		this.npc.getTrait(blm.class).setAggroLoc(this.npc.getEntity().getLocation());
		this.npc.getTrait(blm.class).setTarget((Player)aTarget); 
		return (LivingEntity) aTarget;
	    }
	}
	return null;
    }

    @EventHandler
    public void onFinish(NavigationCompleteEvent event)
    {
	forceFinish = true;
    }

    @Override
    public void reset()
    {
	forceFinish = false;
	this.npc.getTrait(blm.class).setTarget((RPGPlayer)null); 
	this.npc.getTrait(blm.class).setAggroLoc(null);
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

    private void pursueEntity(LivingEntity from, LivingEntity at)
    {
	if (from == null || at == null || from.getWorld() != at.getWorld())
	{
	    return;
	}
	Location loc = from.getLocation();
	double xDiff = at.getLocation().getX() - loc.getX();
	double yDiff = at.getLocation().getY() - loc.getY();
	double zDiff = at.getLocation().getZ() - loc.getZ();
	double distanceXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
	double distanceY = Math.sqrt(distanceXZ * distanceXZ + yDiff * yDiff);
	double x2Diff = npc.getTrait(blm.class).getAggroLoc().getX() - loc.getX();
	double z2Diff = npc.getTrait(blm.class).getAggroLoc().getZ() - loc.getZ();
	double aggroDist = Math.sqrt(x2Diff * x2Diff + z2Diff * z2Diff);
	if (aggroDist > 30 || at == null || at.isDead())
	{
	    reset();
	}
	else if (distanceXZ < 2)
	{
	    if (lastAttack == 0)
	    {
		Bukkit.getPluginManager().callEvent(new MobHitPlayerEvent(npc.getTrait(blm.class).getTarget().getPlayer(), npc));
		lastAttack = System.currentTimeMillis();
	    }
	    else if (System.currentTimeMillis() - lastAttack > 1500)
	    {
		Bukkit.getPluginManager().callEvent(new MobHitPlayerEvent(npc.getTrait(blm.class).getTarget().getPlayer(), npc));
		lastAttack = System.currentTimeMillis();
	    }
	}
	double yaw = (Math.acos(xDiff / distanceXZ) * 180 / Math.PI);
	double pitch = (Math.acos(yDiff / distanceY) * 180 / Math.PI) - 90;
	if (zDiff < 0.0)
	{
	    yaw = yaw + (Math.abs(180 - yaw) * 2);
	}
	net.citizensnpcs.util.NMS.look((LivingEntity) from, (float) yaw - 90, (float) pitch);
    }

    @Override
    public boolean shouldExecute()
    {
	if (npc.getTrait(blm.class).getTarget() != null)
	{
	    pursueEntity((LivingEntity) this.npc.getEntity(), (LivingEntity) npc.getTrait(blm.class).getTarget().getPlayer());
	    return false;
	}
	else
	{
	    LivingEntity target = findTarget(15);
	    if (target != null)
	    {
		pursueEntity((LivingEntity) this.npc.getEntity(), target);
		return false;
	    }
	    else
	    {
		if (!npc.isSpawned() || npc.getNavigator().isNavigating())
		{
		    return false;
		}
		Location dest = findRandomPosition();
		if (dest == null)
		{
		    return false;
		}
		npc.getNavigator().setTarget(dest);
		return true;
	    }
	}
    }

    public static AggressiveWanderGoal createWithNPC(NPC npc)
    {
	return createWithNPCAndRange(npc, 10);
    }

    public static AggressiveWanderGoal createWithNPCAndRange(NPC npc, int xrange)
    {
	return new AggressiveWanderGoal(npc, xrange);
    }

    @SuppressWarnings(
    { "deprecation" })
    public CompletableFuture<Boolean> canSpawning(Location startTarget)
    {
	WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
	CompletableFuture<Boolean> futureValue = new CompletableFuture<Boolean>();
	futureValue = CompletableFuture.supplyAsync(() -> {
	    if (wg == null || startTarget == null)
	    {
		return false;
	    }
	    return wg.getRegionManager(startTarget.getWorld()).getApplicableRegions(startTarget).allows(DefaultFlag.MOB_SPAWNING);
	});
	return futureValue;
    }
}