package com.strongholdmc.blevents.combatevents;
import java.util.List;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import com.strongholdmc.blcore.main.RPGStat;
import com.strongholdmc.blcore.playerclasses.RPGItems;
import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blcore.storage.RPGPlayers;
public class AttackWithWeaponEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private PlayerHitMobEvent e;
    private com.strongholdmc.blcore.playerclasses.RPGWeapon w;

    public AttackWithWeaponEvent(PlayerHitMobEvent e)
    {
	this.e = e;
	List<String> lore = e.getAttacker().getItemInHand().getItemMeta().getLore();
	for(String s : lore)
	{
	    System.out.println(s);
	}
	w=RPGItems.getWeapon(lore.get(lore.size()-1));
    }
    
    public Player getAttacker()
    {
	return e.getAttacker();
    }

    public int getMinDmg()
    {
	if(w==null){Bukkit.broadcastMessage("THE WHOLE THING IS NULL MORTY!");}
	return w.getStatValue(RPGStat.LOW_DAMAGE);
    }

    public int getMaxDmg()
    {
	if(w==null){Bukkit.broadcastMessage("THE WHOLE THING IS NULL MORTY!");}
	return w.getStatValue(RPGStat.HIGH_DAMAGE);
    }

    public RPGPlayer getRPlayer()
    {
	return RPGPlayers.getRPGPlayer(e.getAttacker());
    }

    public NPC getDefender()
    {
	return e.getDefender();
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
