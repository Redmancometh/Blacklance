package com.strongholdmc.blevents.combatevents;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blcore.storage.RPGPlayers;

public class PlayerHitMobEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private Player attacker;
    private NPC defender;
    public PlayerHitMobEvent(Player attacker, NPC defender)
    {
	this.attacker=attacker;
	this.defender=defender;
    }
    public Player getAttacker()
    {
	return attacker;
    }
    public RPGPlayer getRPlayer()
    {
	return RPGPlayers.getRPGPlayer(attacker);
    }
    public NPC getDefender()
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
