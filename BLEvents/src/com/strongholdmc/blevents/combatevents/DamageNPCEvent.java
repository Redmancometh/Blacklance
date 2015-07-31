package com.strongholdmc.blevents.combatevents;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blcore.storage.RPGPlayers;
public class DamageNPCEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private Player attacker;
    private NPC defender;
    private double damage;
    private Location loc;
    public DamageNPCEvent(Player attacker, NPC defender, double damage, Location loc)
    {
	this.damage=damage;
	this.attacker = attacker;
	this.defender=defender;
	this.loc=loc;
    }
    public double getDamage()
    {
	return damage;
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
    public Location getLocation()
    {
	return loc;
    }
}
