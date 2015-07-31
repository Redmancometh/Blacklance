package com.strongholdmc.blevents.listeners;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blevents.combatevents.MobHitPlayerEvent;
import com.strongholdmc.blevents.combatevents.NPCMeleeEvent;
import com.strongholdmc.blevents.combatevents.PlayerHitMobEvent;
import com.strongholdmc.blmobs.trait.blm;

public class SpecificMeleeListener implements Listener
{
    @EventHandler
    public void onMelee(NPCMeleeEvent event)
    {
	new BukkitRunnable()
	{
	    public void run()
	    {
		if (event.getAttacker() instanceof Player)
		{
		    NPC defender = CitizensAPI.getNPCRegistry().getNPC(event.getDefender());
		    if (defender.hasTrait(blm.class))
		    {
			PlayerHitMobEvent e = new PlayerHitMobEvent((Player) event.getAttacker(), defender);
			Bukkit.getPluginManager().callEvent(e);
		    }
		}
		else if (event.getDefender() instanceof Player)
		{
		    NPC attacker = CitizensAPI.getNPCRegistry().getNPC(event.getAttacker());
		    if (attacker.hasTrait(blm.class))
		    {
			MobHitPlayerEvent e = new MobHitPlayerEvent((Player) event.getDefender(), attacker);
			Bukkit.getPluginManager().callEvent(e);
		    }
		}
		else
		{
		    System.out.println("NPCMeleeEvent called incorrectly!");
		    return;
		}
	    }
	}.runTaskAsynchronously(BlackLance.getPlugin());

    }
}
