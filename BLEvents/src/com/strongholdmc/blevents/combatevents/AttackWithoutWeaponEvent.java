package com.strongholdmc.blevents.combatevents;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blcore.storage.RPGPlayers;
import com.strongholdmc.blmobs.trait.blm;
public class AttackWithoutWeaponEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private Player attacker;
    private NPC defender;

    public AttackWithoutWeaponEvent(Player attacker, NPC defender)
    {
	this.attacker = attacker;
	this.defender=defender;
	defender.getTrait(blm.class).removeMobHealth(20, attacker);
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
