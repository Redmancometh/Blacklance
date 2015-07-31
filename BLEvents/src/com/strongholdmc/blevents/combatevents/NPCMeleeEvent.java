package com.strongholdmc.blevents.combatevents;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class NPCMeleeEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private Entity attacker;
    private Entity defender;
    public NPCMeleeEvent(Entity attacker, Entity defender)
    {
	this.attacker=attacker;
	this.defender=defender;
    }
    public Entity getAttacker()
    {
	return attacker;
    }
    public Entity getDefender()
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