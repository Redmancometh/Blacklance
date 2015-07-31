package com.strongholdmc.blmerchants.listeners;

import net.citizensnpcs.api.event.NPCRightClickEvent;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import com.strongholdmc.blevents.MerchantEvents.MerchantBuyEvent;
import com.strongholdmc.blevents.MerchantEvents.MerchantSellEvent;
import com.strongholdmc.blmerchants.caching.CachedVars;
import com.strongholdmc.blmerchants.trait.BLMerchant;

public class MerchantListener implements Listener
{
    private CachedVars cacheProc;

    public MerchantListener(CachedVars cacheProc)
    {
	this.cacheProc = cacheProc;
    }

    @EventHandler
    public void onRightClick(NPCRightClickEvent e)
    {
	if (e.getNPC().hasTrait(BLMerchant.class))
	{
	    e.getClicker().openInventory(cacheProc.getInventory(e.getNPC().getTrait(BLMerchant.class).getID()));
	}
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBuyItem(MerchantBuyEvent e)
    {
	try
	{
	    Economy.subtract(e.getPlayer().getName(), e.getValue());
	    e.getPlayer().getInventory().addItem(e.getSanitizedItem());
	}
	catch (NoLoanPermittedException e1)
	{
	    ((Player) e.getPlayer()).sendMessage(ChatColor.DARK_RED + "You don't have enough money!");
	    return;
	}
	catch (UserDoesNotExistException e1)
	{
	    return;
	}
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onSellItem(MerchantSellEvent e)
    {
	if (e.getValue() == 0)
	{
	    e.getPlayer().sendMessage(ChatColor.DARK_RED + "You cannot sell this item to the shop!");
	}
	else
	{
	    try
	    {
		ItemStack i = e.getItem();
		int value = e.getValue();
		Economy.add(e.getPlayer().getName(), value);
		e.getPlayer().getInventory().removeItem(i);
		e.getPlayer().sendMessage(ChatColor.GREEN+"You sold "+ChatColor.DARK_GREEN+getSanitizedString(i)+ChatColor.GREEN+" for $"+value);
	    }
	    catch (NoLoanPermittedException e1)
	    {
		return;
	    }
	    catch (UserDoesNotExistException e1)
	    {
		return;
	    }
	}
    }
    public String getSanitizedString(ItemStack i)
    {
	String s = i.getItemMeta().getDisplayName();
	if(s.endsWith(" "))
	{
	    s = s.substring(0,s.length() - 1);
	}
	return s;
    }
}
