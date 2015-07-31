package com.strongholdmc.blcore.listeners;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.metadata.MetadataValue;
import com.strongholdmc.blcore.playerclasses.RPGWeapon;
public class WeaponListener implements Listener
{
    @EventHandler
    public void pickupItem(PlayerPickupItemEvent e)
    {
	boolean ownerDrop = false;
	for (MetadataValue value : e.getItem().getMetadata("killer"))
	{
	    if (value.asString().equals(e.getPlayer().getUniqueId().toString()))
	    {
		ownerDrop=true;
	    }
	}
	if (ownerDrop)
	{
	    if (RPGWeapon.isWeapon(e.getItem().getItemStack().getItemMeta().getLore()))
	    {
		RPGWeapon.makeWeapon(e.getItem().getItemStack());
	    }
	}
	else
	{
	    e.setCancelled(true);
	}
    }
}
