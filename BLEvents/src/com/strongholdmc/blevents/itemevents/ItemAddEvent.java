package com.strongholdmc.blevents.itemevents;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
public final class ItemAddEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private Player p;
    private ItemStack[] added;
    private ItemStack[] overFlow;
    public ItemAddEvent(Player p, ItemStack[] overFlow, ItemStack[] added)
    {
	this.p=p;
	this.added=added;
	this.overFlow=overFlow;
    }
    public Player getPlayer()
    {
	return p;
    }
    public boolean hasOverlow()
    {
	if(overFlow.length>0)
	{
	    return true;
	}
	else
	{
	    return false;
	}
    }
    public ItemStack[] getOverflow()
    {
	return overFlow;
    }
    public ItemStack[] getAdded()
    {
	return this.added;
    }
    public HandlerList getHandlers()
    {
	return handlers;
    }
    public static HandlerList getHandlerList()
    {
	return handlers;
    }
}