package com.strongholdmc.blevents.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blevents.combatevents.AttackWithWeaponEvent;
import com.strongholdmc.blevents.combatevents.AttackWithoutWeaponEvent;
import com.strongholdmc.blevents.combatevents.PlayerHitMobEvent;

public class WeaponListener implements Listener
{
    @EventHandler
    public void onPlayerHit(PlayerHitMobEvent e)
    {
	new BukkitRunnable()
	{
	    public void run()
	    {
		Player p = e.getAttacker();
		if (p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore())
		{
		    if (p.getItemInHand().getItemMeta().getLore().toString().contains("Damage"))
		    {
			Bukkit.getPluginManager().callEvent(new AttackWithWeaponEvent(e));
		    }
		    else
		    {
			Bukkit.getPluginManager().callEvent(new AttackWithoutWeaponEvent(p, e.getDefender()));
		    }
		}
		else
		{
		    Bukkit.getPluginManager().callEvent(new AttackWithoutWeaponEvent(p, e.getDefender()));
		}
	    }
	}.runTaskAsynchronously(BlackLance.getPlugin());
    }
}
