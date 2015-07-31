package com.strongholdmc.blevents.MerchantEvents;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
public final class MerchantBuyEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private Player p;
    private ItemStack i;    
    public MerchantBuyEvent(Player p, ItemStack i)
    {
	this.i=i;
	this.p=p;
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
	List<String> lore = i.getItemMeta().getLore();
	int cost = Integer.parseInt(lore.get(lore.size()-1).replaceAll("§", ""));
	return cost;
    }
    
    public ItemStack getSanitizedItem()
    {
	ItemStack newI = i.clone();
	ItemMeta iMeta = newI.getItemMeta();
	List<String> lore = newI.getItemMeta().getLore();
	lore.remove(lore.size()-1);
	lore.remove(lore.size()-1);
	lore.add(lore.size(), ChatColor.BLUE+"Sell Value: "+getValue()/4);
	iMeta.setLore(lore);
	newI.setItemMeta(iMeta);
	return newI;
    }
}