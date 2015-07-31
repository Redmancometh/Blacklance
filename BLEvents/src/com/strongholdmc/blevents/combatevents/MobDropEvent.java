package com.strongholdmc.blevents.combatevents;
import org.bukkit.entity.Item;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class MobDropEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private Item i;
    private ItemStack itemStack;
    public MobDropEvent(Item i)
    {
	this.i=i;
	this.itemStack=i.getItemStack();
    }
    public HandlerList getHandlers()
    {
	return handlers;
    }
    public static HandlerList getHandlerList()
    {
	return handlers;
    }
    public Item getItem()
    {
	return i;
    }
    public ItemStack getItemStack()
    {
	return itemStack;
    }
}
