package com.strongholdmc.blevents.listeners;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blevents.combatevents.NPCMeleeEvent;

public class GeneralMeleeListener implements Listener
{
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onHit(EntityDamageByEntityEvent e)
    {
	e.setCancelled(true);
	new BukkitRunnable()
	{
	    public void run()
	    {
		e.setDamage(0);
		if (!(e.getDamager() instanceof Projectile || e.getEntity() instanceof Projectile))
		{
		    boolean isNPC = CitizensAPI.getNPCRegistry().isNPC(e.getDamager()) || CitizensAPI.getNPCRegistry().isNPC(e.getEntity());
		    if (isNPC)
		    {
			NPCMeleeEvent nme = new NPCMeleeEvent(e.getDamager(), e.getEntity());
			Bukkit.getServer().getPluginManager().callEvent(nme);
		    }
		}
	    }
	}.runTaskAsynchronously(BlackLance.getPlugin());

    }
    @EventHandler(priority=EventPriority.MONITOR)
    public void onHit2(NPCDamageByEntityEvent e)
    {
	e.setDamage(0);
    }
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onHit3(EntityDamageByEntityEvent e)
    {
	e.setDamage(0);
    }
}
