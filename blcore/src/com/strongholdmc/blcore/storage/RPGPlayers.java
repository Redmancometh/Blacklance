package com.strongholdmc.blcore.storage;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

import com.strongholdmc.blcore.playerclasses.RPGPlayer;
public class RPGPlayers
{
    public static HashMap<UUID, RPGPlayer> RPGPlayers = new HashMap<UUID,RPGPlayer>();

    public static HashMap<UUID, RPGPlayer> getFullMap()
    {
	return RPGPlayers;
    }

    public static RPGPlayer getRPGPlayer(Player p)
    {
	RPGPlayer rp = RPGPlayers.get(p.getUniqueId());
	return rp;
    }

    public static void addRPGPlayer(Player p, RPGPlayer rp)
    {
	RPGPlayers.put(p.getUniqueId(), rp);
    }
    public static double getXP(Player p)
    {
	return RPGPlayers.get(p.getUniqueId()).getXP();
    }

}
