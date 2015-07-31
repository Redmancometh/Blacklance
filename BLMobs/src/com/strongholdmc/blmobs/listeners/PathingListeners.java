package com.strongholdmc.blmobs.listeners;
import java.util.concurrent.CompletableFuture;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.goals.WanderGoal;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

public class PathingListeners implements Listener
{
   /* @EventHandler
    public void onNav(NavigationBeginEvent e)
    {
	NPC npc = e.getNPC();	
	if(npc.getNavigator().isNavigating()){return;}
	setRandomGoal(npc, 3);
    }*/
    
    public void setRandomGoal(NPC npc, int xRange)
    {
	WanderGoal goal = WanderGoal.createWithNPCAndRange(npc, xRange, 1);
	CitizensAPI.registerEvents(goal);
	npc.getDefaultGoalController().addGoal(goal, 1);
    }
    
    @SuppressWarnings(
    { "deprecation" })
    public CompletableFuture<Boolean> canSpawning(WorldGuardPlugin wg, Location startTarget)
    {
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
