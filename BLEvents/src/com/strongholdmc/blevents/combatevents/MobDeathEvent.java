package com.strongholdmc.blevents.combatevents;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blcore.storage.RPGPlayers;
public final class MobDeathEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private Player attacker;
    private NPC killed;
    private double experience;
    public MobDeathEvent(Player attacker, NPC killed, double experience)
    {
	this.killed=killed;
	this.attacker = attacker;
	this.setExperience(experience);
    }
    public Player getAttacker()
    {
	return attacker;
    }
    public RPGPlayer getRPlayer()
    {
	return RPGPlayers.getRPGPlayer(attacker);
    }
    public NPC getKilled()
    {
	return killed;
    }
    public HandlerList getHandlers()
    {
	return handlers;
    }
    public static HandlerList getHandlerList()
    {
	return handlers;
    }
    public double getExperience()
    {
	return experience;
    }
    public void setExperience(double experience)
    {
	this.experience = experience;
    }
}
