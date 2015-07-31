package com.strongholdmc.blmobs.listeners;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blevents.mobevents.BLSpawnEvent;
import com.strongholdmc.blmobs.config.ConfigUtil;
import com.strongholdmc.blmobs.config.Region;
import com.strongholdmc.blmobs.trait.blm;
import com.strongholdmc.blmobs.util.AsyncMobUtils;
public class MobSpawnListener implements Listener
{
    private ConfigUtil zones;
    private WorldGuardPlugin wg;

    public MobSpawnListener(ConfigUtil zones)
    {
	this.zones = zones;
	this.wg = (WorldGuardPlugin) Bukkit.getPluginManager().getPlugin("WorldGuard");
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onSpawn(CreatureSpawnEvent e) throws InterruptedException, ExecutionException
    {
	if (!CitizensAPI.getNPCRegistry().isNPC(e.getEntity()))
	{
	    e.setCancelled(true);
	}
	new BukkitRunnable()
	{
	    public void run()
	    {
		try
		{
		    if (!checkMobs(e.getEntity()).get())
		    {
			return;
		    }
		    else
		    {
			spawnMob(e.getLocation());
		    }
		}
		catch (InterruptedException | ExecutionException e1)
		{
		    e1.printStackTrace();
		}
	    }
	}.runTaskAsynchronously(BlackLance.getPlugin());
    }

    @EventHandler
    public void makeMob(BLSpawnEvent e)
    {
	new BukkitRunnable()
	{
	    @SuppressWarnings("deprecation")
	    public void run()
	    {
		int level = e.getLevel();
		boolean aggressive = e.getAggressive();
		Location loc = e.getLocation();
		String mobName = e.getName();
		NPC npc = CitizensAPI.getNPCRegistry().createNPC(e.getType(), "[" + level + "]" + " " + getColor(aggressive) + mobName);
		npc.addTrait(blm.class);
		blm blm = npc.getTrait(blm.class);
		blm.setAggressive(aggressive);
		blm.setLevel(level);
		npc.spawn(loc);
		npc.getBukkitEntity().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 5000000, 3));
	    }
	}.runTask(BlackLance.getPlugin());
    }

    public String getWGRegion(Location loc1)
    {
	for (ProtectedRegion rg : wg.getRegionManager(loc1.getWorld()).getApplicableRegions(loc1).getRegions())
	{
	    return rg.getId();
	}
	return null;
    }

    public void spawnMob(Location loc)
    {
	String regionName = getWGRegion(loc);
	if (regionName == null)
	{
	    return;
	}
	try
	{
	    Region r = zones.getRegion(regionName);
	    Bukkit.getPluginManager().callEvent(new BLSpawnEvent(r.getRandomMob(), loc));
	}
	catch (Throwable t)
	{
	    t.printStackTrace();
	}
    }

    public ChatColor getColor(boolean aggressive)
    {
	if (aggressive)
	{
	    return ChatColor.DARK_RED;
	}
	else
	{
	    return ChatColor.YELLOW;
	}
    }

    @SuppressWarnings("deprecation")
    public CompletableFuture<Boolean> checkMobs(Entity e)
    {
	CompletableFuture<Boolean> futureValue = new CompletableFuture<Boolean>();
	List<Entity> entities;
	try
	{
	    entities = AsyncMobUtils.getEntitiesForAsync(e, 20, 5, 20).get();
	    futureValue = CompletableFuture.supplyAsync(() -> {
		int totalMobs = 0;
		for (Entity entity : entities)
		{
		    if (entity instanceof LivingEntity)
		    {
			totalMobs++;
		    }
		}
		boolean canSpawn = wg.getRegionManager(e.getLocation().getWorld()).getApplicableRegions(e.getLocation()).allows(DefaultFlag.MOB_SPAWNING);
		if (totalMobs < 100 && canSpawn)
		{
		    return true;
		}
		else
		{
		    return false;
		}
	    });
	}
	catch (InterruptedException | ExecutionException e1)
	{
	    e1.printStackTrace();
	}
	return futureValue;
    }
}
