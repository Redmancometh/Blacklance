package com.strongholdmc.blcore.party;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blcore.storage.RPGPlayers;
public class PartyCommands implements CommandExecutor
{
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
    {
	Player p = (Player) sender;
	RPGPlayer rp = RPGPlayers.getRPGPlayer(p);
	if (cmd.getName().equalsIgnoreCase("party") || cmd.getName().equalsIgnoreCase("p"))
	{
	    if (args.length >= 1)
	    {
		if(Parties.parties.containsKey(rp))
		{
		    String message = "";
		    for (String s : args)
		    {
			message += " " + s;
		    }
		    Parties.getPartyByPlayer(p).sendMessage(message, rp);
		}
	    }
	}
	if (cmd.getName().equalsIgnoreCase("acceptinvite"))
	{
	    if (rp.invited != null){rp.invited.acceptInvite(rp);}
	    else
	    {
		p.sendMessage(ChatColor.DARK_RED + "You have no pending invites");
	    }
	}
	if(cmd.getName().equalsIgnoreCase("declineinvite"))
	{
	    if (rp.invited != null){rp.invited.declineInvite(rp);}
	    else
	    {
		p.sendMessage(ChatColor.DARK_RED + "You have no pending invites");
	    }
	}
	if (cmd.getName().equalsIgnoreCase("invite"))
	{
	    if (args.length < 1)
	    {
		p.sendMessage(ChatColor.DARK_RED + "Please enter the name of who you want to invite!");
		return false;
	    }
	    else
	    {
		Player invitee = Bukkit.getServer().getPlayer(args[0]);
		if (invitee != null)
		{
		    if (Parties.parties.containsKey(rp))
		    {
			p.sendMessage(ChatColor.BLUE + "You have invited " + invitee.getDisplayName() + " to your party!");
			Party party = Parties.getPartyByPlayer(p);
			if(party.isLeader(rp)){party.invitePlayer(RPGPlayers.getRPGPlayer(invitee));}
			else{p.sendMessage(ChatColor.DARK_RED+"You are not the party leader!");}
		    }
		    else
		    {
			p.sendMessage(ChatColor.DARK_RED + "You are not in a party!");
		    }
		}
	    }
	}
	if (cmd.getName().equalsIgnoreCase("makeparty"))
	{
	    if(!Parties.parties.containsKey(rp))
	    {
		Party.CreateParty(RPGPlayers.getRPGPlayer(p));
		p.sendMessage(ChatColor.BLUE+"You have formed a party!");
	    }
	    else{p.sendMessage(ChatColor.DARK_RED+"You are already in a party!");}
	}
	if(cmd.getName().equalsIgnoreCase("leaveparty"))
	{
	    if(Parties.parties.containsKey(rp))
	    {
		Parties.parties.get(rp).removePartyMember(rp, "left");
	    }
	}
	if(cmd.getName().equalsIgnoreCase("kickfromparty"))
	{
	    if(args.length==1)
	    {
		Player toKick = Bukkit.getServer().getPlayer(args[0]);
		if(toKick!=null)
		{
		    RPGPlayer kicked = RPGPlayers.getRPGPlayer(toKick);
		    if(Parties.parties.containsKey(kicked))
		    {
			Parties.parties.get(kicked).removePartyMember(kicked, "kicked");
		    }
		}
	    }
	}
	return true;
    }
}
