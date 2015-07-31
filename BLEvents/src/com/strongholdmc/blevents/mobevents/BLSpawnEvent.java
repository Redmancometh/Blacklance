package com.strongholdmc.blevents.mobevents;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.strongholdmc.blmobs.config.BLEntity;

public final class BLSpawnEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    BLEntity e;
    private Location loc1;
    public BLSpawnEvent(BLEntity e, Location loc1)
    {
	this.e=e;
	this.loc1=loc1;
    }
    public Location getLocation()
    {
	return loc1;
    }
    public String getName()
    {
	return e.getName();
    }

    public int getLevel()
    {
	return e.getLevel();
    }

    public boolean getAggressive()
    {
	return e.isAggressive();
    }

    public HandlerList getHandlers()
    {
	return handlers;
    }

    public static HandlerList getHandlerList()
    {
	return handlers;
    }
    public EntityType getType()
    {
	return e.getType();
    }
}
