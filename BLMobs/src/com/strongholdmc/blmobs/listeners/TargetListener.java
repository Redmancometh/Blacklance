package com.strongholdmc.blmobs.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.strongholdmc.blevents.combatevents.PlayerHitMobEvent;
import com.strongholdmc.blmobs.trait.blm;

public class TargetListener implements Listener
{
    @EventHandler
    public void onHit(PlayerHitMobEvent e)
    {
	if(e.getDefender().hasTrait(blm.class))
	{
	    e.getDefender().getTrait(blm.class).setTarget(e.getAttacker());
	    e.getDefender().getTrait(blm.class).setAggroLoc(e.getAttacker().getLocation());
	}
    }
}
