package com.strongholdmc.blmobs.config;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.EntityType;

//Abstract later for future effects
public class Region
{
    private String name;
    private int minLvl;
    private int maxLvl;
    private HashMap<String, EntityType> entities = new HashMap<String, EntityType>();
    private List<String> entityNameList = new ArrayList<String>();
    private boolean aggressive;
    public Region(String name, int minLvl, int maxLvl, boolean aggressive)
    {
	this.name = name;
	this.minLvl = minLvl;
	this.maxLvl = maxLvl;
	this.aggressive=aggressive;
    }

    @SuppressWarnings("deprecation")
    public void insertAllEntities(List<String> entityNames)
    {
	for (String entityName : entityNames)
	{
	    String[] names = entityName.split(",");
	    entityNameList.add(names[0]);
	    entities.put(names[0], EntityType.fromName(names[1]));
	}
    }

    public BLEntity getRandomMob()
    {
	Collections.shuffle(entityNameList);
	return new BLEntity(entities.get(entityNameList.get(0)), entityNameList.get(0), getRandomLevel(), aggressive);
    }

    public String getName()
    {
	return name;
    }

    public void setName(String name)
    {
	this.name = name;
    }

    public int getMinLvl()
    {
	return minLvl;
    }

    public void setMinLvl(int minLvl)
    {
	this.minLvl = minLvl;
    }

    public int getMaxLvl()
    {
	return maxLvl;
    }

    public void setMaxLvl(int maxLvl)
    {
	this.maxLvl = maxLvl;
    }

    public int getRandomLevel()
    {
	return minLvl + (int) (Math.random() * ((maxLvl - minLvl) + 1)) + 1;
    }

    public boolean isAggressive()
    {
	return aggressive;
    }

    public void setAggressive(boolean aggressive)
    {
	this.aggressive = aggressive;
    }

}
