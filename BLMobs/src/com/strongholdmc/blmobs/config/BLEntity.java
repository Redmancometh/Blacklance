package com.strongholdmc.blmobs.config;
import org.bukkit.entity.EntityType;
public class BLEntity
{
    private EntityType type;
    private String name;
    private int level;
    private boolean aggressive;
    public BLEntity(EntityType type, String name, int level, boolean aggressive)
    {
	this.name=name;
	this.type=type;
	this.setLevel(level);
	this.setAggressive(aggressive);
    }
    public EntityType getType()
    {
	return type;
    }
    public void setType(EntityType type)
    {
	this.type = type;
    }
    public String getName()
    {
	return name;
    }
    public void setName(String name)
    {
	this.name = name;
    }
    public int getLevel()
    {
	return level;
    }
    public void setLevel(int level)
    {
	this.level = level;
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
