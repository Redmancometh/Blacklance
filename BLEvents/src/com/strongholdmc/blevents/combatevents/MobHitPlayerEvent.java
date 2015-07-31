package com.strongholdmc.blevents.combatevents;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blcore.storage.RPGPlayers;
public final class MobHitPlayerEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private NPC attacker;
    private Player defender;

    public MobHitPlayerEvent(Player defender, NPC attacker)
    {
	this.attacker = attacker;
	this.defender = defender;
    }
    
    public NPC getAttacker()
    {
	return attacker;
    }

    public RPGPlayer getRPlayer()
    {
	return RPGPlayers.getRPGPlayer(defender);
    }

    public Player getDefender()
    {
	return defender;
    }

    public HandlerList getHandlers()
    {
	return handlers;
    }

    public static HandlerList getHandlerList()
    {
	return handlers;
    }
}
