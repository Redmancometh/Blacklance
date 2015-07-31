package com.strongholdmc.blcore.party;

import java.util.HashMap;

import org.bukkit.entity.Player;

import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blcore.storage.RPGPlayers;

public class Parties
{
    public static HashMap<RPGPlayer, Party> parties = new HashMap<RPGPlayer, Party>();
    public static void removeParty(Party party){parties.values().remove(party);}
    public static Party getPartyByPlayer(Player p){return parties.get(RPGPlayers.getRPGPlayer(p));}
}
