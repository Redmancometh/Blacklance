package com.strongholdmc.blmobs.util;

import java.util.List;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.strongholdmc.blcore.main.BlackLance;
public class MetaDataUtil
{
    public static String getMetaString(Entity target, String key)
    {
	List<MetadataValue> md = target.getMetadata(key);
	return md.get(0).asString();
    }
    public static double getMetaDouble(Entity target, String key)
    {
	List<MetadataValue> md = target.getMetadata(key);
	double cooldown = 0;
	if(md.size()>0){cooldown = md.get(0).asDouble();}
	return cooldown;
    }
    public static boolean getMetaBool(Entity target, String key)
    {
	List<MetadataValue> md = target.getMetadata(key);
	if(md.size()>0){return md.get(0).asBoolean();}
	return false;
    }
    public static void setMetaString(Entity e, String key, String value)
    {
	e.setMetadata(key, new FixedMetadataValue(BlackLance.getPlugin(), value));
    }
    public static void setMetaDouble(Entity e, String key, double value)
    {
	e.setMetadata(key, new FixedMetadataValue(BlackLance.getPlugin(), value));
    }
    public static void setMetaBool(Entity e, String key, boolean value)
    {
	e.setMetadata(key, new FixedMetadataValue(BlackLance.getPlugin(), value));
    }
}
