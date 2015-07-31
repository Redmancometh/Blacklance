package com.strongholdmc.blmobs.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.strongholdmc.blcore.main.BlackLance;

public class ConfigUtil
{
    private FileConfiguration drops;
    private FileConfiguration zones;
    List<String> zoneList = new ArrayList<String>();
    Multimap<Integer, List<String>> prefixes = ArrayListMultimap.create();
    Multimap<Integer, List<String>> suffixes = ArrayListMultimap.create();
    Multimap<Integer, Integer> minDmg = ArrayListMultimap.create();
    Multimap<Integer, Integer> maxDmg = ArrayListMultimap.create();
    Multimap<Integer, List<Material>> types = ArrayListMultimap.create();
    HashMap<String, Region> regions = new HashMap<String, Region>();
    public ConfigUtil(FileConfiguration zones, FileConfiguration drops)
    {
	this.zones = zones;
	this.drops = drops;
    }

    public Region getRegion(String zoneName)
    {
	return regions.get(zoneName);
    }
        
    public void setAllRegions()
    {
	for (String name : zoneList)
	{
	    try
	    {
		Region r = new Region(name, minLevel(name).get(), maxLevel(name).get(), getAggressive(name).get());
		Bukkit.broadcastMessage(""+getAggressive(name));
		r.insertAllEntities(getNames(name).get());
		regions.put(name, r);
	    }
	    catch (Throwable t)
	    {
		t.printStackTrace();
	    }
	}
    }

    public CompletableFuture<List<String>> getNames(String zoneName)
    {
	CompletableFuture<List<String>> futureValue = CompletableFuture.supplyAsync(() -> {
	    return zones.getStringList("Region." + zoneName + ".MobNames");
	});
	return futureValue;
    }

    public CompletableFuture<Integer> minLevel(String zoneName)
    {
	CompletableFuture<Integer> futureValue = CompletableFuture.supplyAsync(() -> {
	    return zones.getInt("Region." + zoneName + ".Minlvl");
	});
	return futureValue;
    }

    public CompletableFuture<Integer> maxLevel(String zoneName)
    {
	CompletableFuture<Integer> futureValue = CompletableFuture.supplyAsync(() -> {
	    return zones.getInt("Region." + "zoneName" + ".Maxlvl");
	});
	return futureValue;
    }

    public CompletableFuture<Boolean> getAggressive(String zoneName)
    {
	CompletableFuture<Boolean> futureValue = CompletableFuture.supplyAsync(() -> {
	    return zones.getBoolean("Region." + zoneName + ".Aggressive");
	});
	return futureValue;
    }

    public CompletableFuture<String> getRandomName(String zoneName)
    {
	CompletableFuture<String> futureValue = CompletableFuture.supplyAsync(() -> {
	    List<String> names = zones.getStringList("Region." + zoneName + ".MobNames");
	    Collections.shuffle(names);
	    return names.get(0);
	});
	return futureValue;
    }

    public void getAllRegions()
    {
	zoneList = zones.getStringList("RegionList");
    }

    public CompletableFuture<Integer> getRandomLevel(String zoneName)
    {
	CompletableFuture<Integer> futureValue = CompletableFuture.supplyAsync(() -> {
	    try
	    {
		int Min = maxLevel(zoneName).get();
		int Max = minLevel(zoneName).get();
		return Min + (int) (Math.random() * ((Max - Min) + 1)) + 1;
	    }
	    catch (Throwable t)
	    {
		return null;
	    }
	});
	return futureValue;
    }

    public void setDropInfo(int minLevel)
    {
	new BukkitRunnable()
	{
	    public void run()
	    {
		for (int x = 0; x < 100; x += 10)
		{
		    try
		    {
			prefixes.put(x, getPrefixes(x).get());
			suffixes.put(x, getSuffixes(x).get());
			minDmg.put(x, getMinDmg(x).get());
			maxDmg.put(x, getMaxDmg(x).get());
			types.put(x, getTypes(x).get());
		    }
		    catch (InterruptedException | ExecutionException e)
		    {
			e.printStackTrace();
		    }
		}
	    }
	}.runTaskAsynchronously(BlackLance.getPlugin());
    }

    public CompletableFuture<List<Material>> getTypes(int level)
    {
	@SuppressWarnings(
	{ "unchecked", "deprecation" })
	CompletableFuture<List<Material>> futureValue = CompletableFuture.supplyAsync(() -> {
	    List<Material> materials = new ArrayList<Material>();
	    for (int id : (List<Integer>) drops.getList("Drops." + level + ".Prefixes"))
	    {
		materials.add(Material.getMaterial(id));
	    }
	    return materials;
	});
	return futureValue;
    }

    public CompletableFuture<List<String>> getPrefixes(int level)
    {
	CompletableFuture<List<String>> futureValue = CompletableFuture.supplyAsync(() -> {
	    return drops.getStringList("Drops." + level + ".Prefixes");
	});
	return futureValue;
    }

    public CompletableFuture<List<String>> getSuffixes(int level)
    {
	CompletableFuture<List<String>> futureValue = CompletableFuture.supplyAsync(() -> {
	    return drops.getStringList("Drops." + level + ".Suffixes");
	});
	return futureValue;
    }

    public CompletableFuture<Integer> getMinDmg(int level)
    {
	CompletableFuture<Integer> futureValue = CompletableFuture.supplyAsync(() -> {
	    return drops.getInt("Drops." + level + ".MinDmg");
	});
	return futureValue;
    }

    public CompletableFuture<Integer> getMaxDmg(int level)
    {
	CompletableFuture<Integer> futureValue = CompletableFuture.supplyAsync(() -> {
	    return drops.getInt("Drops." + level + ".MaxDmg");
	});
	return futureValue;
    }
}
