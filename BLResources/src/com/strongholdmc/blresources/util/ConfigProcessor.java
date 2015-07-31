package com.strongholdmc.blresources.util;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import com.strongholdmc.blresources.main.Main;

public class ConfigProcessor
{
    private FileConfiguration nodes;
    private List<Location> oreLocations = new ArrayList<Location>();
    private HashMap<Location, Boolean> hayLocations = new HashMap<Location, Boolean>();
    public ConfigProcessor(FileConfiguration nodes)
    {
	this.nodes=nodes;
    }
    public void addResourceNode(String typeName, String subType, Material type, Location loc1)
    {
	List<String> nodeList = nodes.getStringList(typeName);
	if(typeName.equalsIgnoreCase("hay"))
	{
	    nodeList.add(loc1.getBlockX()+", "+loc1.getBlockY()+", "+loc1.getBlockZ());
	}
	else
	{
	    nodeList.add(loc1.getBlockX()+", "+loc1.getBlockY()+", "+loc1.getBlockZ()+", "+ subType);
	}
	nodes.set(typeName, nodeList);
	Main.saveNodes();
    }
    public void setNodeLists()
    {
	List<String> ore = nodes.getStringList("Ore");
	initOre(ore);
	List<String> hayList = nodes.getStringList("Hay");
	initHay(hayList);
    }
    public void initOre(List<String> ore)
    {
	for(String s : ore)
	{
	    String[] oreStuff = s.split(",");
	    int x = Integer.parseInt(oreStuff[0].replace(" ", ""));
	    int y = Integer.parseInt(oreStuff[1].replace(" ", ""));
	    int z = Integer.parseInt(oreStuff[2].replace(" ", ""));
	    Material type = Material.getMaterial(oreStuff[3].replace(" ", ""));
	    System.out.println(x+" "+y+" "+z+" "+type);
	    Location loc1 = new Location(Bukkit.getWorld("world"),x,y,z);
	    loc1.getBlock().setType(type);
	    oreLocations.add(loc1);
	}
    }
    public void initHay(List<String> hayList)
    {
	for(String s : hayList)
	{
	    String[] oreStuff = s.split(",");
	    int x = Integer.parseInt(oreStuff[0].replace(" ", ""));
	    int y = Integer.parseInt(oreStuff[1].replace(" ", ""));
	    int z = Integer.parseInt(oreStuff[2].replace(" ", ""));
	    Location loc1 = new Location(Bukkit.getWorld("world"),x,y,z);
	    hayLocations.put(loc1, false);
	    loc1.getBlock().setType(Material.AIR);
	}
	setRandomHay();
    }
    public void setRandomHay()
    {
	List<Location> shuffler = new ArrayList<Location>(hayLocations.keySet());
	Collections.shuffle(shuffler);
	for(int x = 0; x<shuffler.size()/3; x++)
	{
	    Location loc = shuffler.get(x);
	    loc.getBlock().setType(Material.HAY_BLOCK);
	    hayLocations.put(loc, true);
	}
    }
}
