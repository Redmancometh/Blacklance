package com.strongholdmc.blmobs.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.strongholdmc.blmobs.util.MetaDataUtil;

public class PickupListener implements Listener
{
    @EventHandler
    public void onPickup(PlayerPickupItemEvent e)
    {
	if(e.getItem().hasMetadata("killer"))
	{
	    String s = MetaDataUtil.getMetaString(e.getItem(), "killer");
	    if(!e.getPlayer().getUniqueId().toString().equalsIgnoreCase(s))
	    {
		e.setCancelled(true);
	    }
	}
    }
}
