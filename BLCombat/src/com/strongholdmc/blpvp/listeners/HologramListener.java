package com.strongholdmc.blpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blevents.combatevents.DamageNPCEvent;

@SuppressWarnings("deprecation")
public class HologramListener implements Listener
{
    @EventHandler
    public void onDamage(DamageNPCEvent e)
    {
	new BukkitRunnable()
	{
	    public void run()
	    {
		Location loc1 = e.getLocation();
		final Hologram hologram = HolographicDisplaysAPI.createIndividualHologram(BlackLance.getPlugin(), loc1.add(0,1,0), e.getAttacker(), ChatColor.RED + ""+ e.getDamage());
		HolographicDisplaysAPI.
		hologram.setLocation(loc1.add(0,1,0));
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(BlackLance.getPlugin(), new Runnable()
		{
		    public void run()
		    {
			hologram.delete();
		    }
		}, 10);

	    }
	}.runTask(BlackLance.getPlugin());
    }
}
