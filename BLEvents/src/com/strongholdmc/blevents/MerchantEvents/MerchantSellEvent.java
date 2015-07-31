package com.strongholdmc.blevents.MerchantEvents;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public final class MerchantSellEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private Player p;
    private ItemStack i;

    public MerchantSellEvent(Player p, ItemStack i)
    {
	this.i = i;
	this.p = p;
    }

    public ItemStack getItem()
    {
	return i;
    }

    public Player getPlayer()
    {
	return p;
    }

    public HandlerList getHandlers()
    {
	return handlers;
    }

    public static HandlerList getHandlerList()
    {
	return handlers;
    }

    public int getValue()
    {
	for (String s : i.getItemMeta().getLore())
	{
	    if (s.contains("Sell Value"))
	    {
		String rawValue = s.split(":")[1];
		rawValue = rawValue.replace(" ", "");
		return Integer.parseInt(rawValue);
	    }
	}
	return 0;
    }
}