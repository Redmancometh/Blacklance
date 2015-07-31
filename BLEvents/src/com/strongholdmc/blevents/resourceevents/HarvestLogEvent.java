package com.strongholdmc.blevents.resourceevents;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
public final class HarvestLogEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private Player harvester;
    private Block b;
    public HarvestLogEvent(Player harvester, Block b)
    {
	this.harvester=harvester;
	this.b=b;
    }
    public Player getPlayer()
    {
	return harvester;
    }
    public HandlerList getHandlers()
    {
	return handlers;
    }
    public static HandlerList getHandlerList()
    {
	return handlers;
    }
    public Block getBlock()
    {
	return b;
    }
}