package com.strongholdmc.blevents.resourceevents;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
public final class MiningEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private Player harvester;
    private Block b;
    private int regenTime;
    public MiningEvent(Player harvester, Block b, int regenTime)
    {
	this.harvester=harvester;
	this.b=b;
	this.regenTime=regenTime;
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
    public int getRegenTime()
    {
	return regenTime;
    }
    public void setRegenTime(int regenTime)
    {
	this.regenTime = regenTime;
    }
}