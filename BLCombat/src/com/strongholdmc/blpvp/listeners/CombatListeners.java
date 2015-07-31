package com.strongholdmc.blpvp.listeners;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blcore.storage.RPGPlayers;
import com.strongholdmc.blevents.combatevents.AttackWithWeaponEvent;
import com.strongholdmc.blevents.combatevents.MobDeathEvent;
import com.strongholdmc.blevents.combatevents.MobHitPlayerEvent;
import com.strongholdmc.blmobs.trait.blm;
import com.strongholdmc.blpvp.util.CombatUtil;

public class CombatListeners implements Listener
{
    Random r = new Random();

    @EventHandler
    public void HitPlayer(MobHitPlayerEvent e)
    {
	new BukkitRunnable()
	{
	    public void run()
	    {
		Player p = e.getDefender();
		RPGPlayer rp = RPGPlayers.getRPGPlayer(p);
		int hit = (int) (CombatUtil.getmobDamage(e.getAttacker()) - CombatUtil.getAbsorption(p));
		rp.damagePlayer((int) hit);
	    }
	}.runTaskAsynchronously(BlackLance.getPlugin());
    }

    @EventHandler
    public void mobDeath(MobDeathEvent e)
    {
	Bukkit.broadcastMessage(e.getExperience() + " exp");
    }

    @EventHandler
    public void HitMob(AttackWithWeaponEvent e)
    {
	new BukkitRunnable()
	{
	    public void run()
	    {
		double hit = (r.nextInt(e.getMinDmg()) + e.getMaxDmg()) + (e.getAttacker().getLevel() * 1.25) + 2;
		e.getDefender().getTrait(blm.class).removeMobHealth((double) hit, e.getAttacker());
	    }
	}.runTaskAsynchronously(BlackLance.getPlugin());
    }

}