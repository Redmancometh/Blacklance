package com.strongholdmc.blcore.party;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.server.v1_8_R1.ChatClickable;
import net.minecraft.server.v1_8_R1.ChatHoverable;
import net.minecraft.server.v1_8_R1.ChatMessage;
import net.minecraft.server.v1_8_R1.ChatModifier;
import net.minecraft.server.v1_8_R1.EnumChatFormat;
import net.minecraft.server.v1_8_R1.EnumClickAction;
import net.minecraft.server.v1_8_R1.EnumHoverAction;
import net.minecraft.server.v1_8_R1.IChatBaseComponent;
import net.minecraft.server.v1_8_R1.PacketPlayOutChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;

import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blcore.playerclasses.RPGPlayer;


public class Party
{
    //CHECK FOR LEADER ON LEAVE!
    private LinkedList<RPGPlayer> partyMembers = new LinkedList<RPGPlayer>();
    public RPGPlayer leader;
    private List<RPGPlayer> invites = new ArrayList<RPGPlayer>();
    Objective objective;
    public Party(RPGPlayer Leader)
    {
	this.leader=Leader;
	partyMembers.add(leader);
    }
    public LinkedList<RPGPlayer> getList(){return partyMembers;}
    public Player[] getPlayers()
    {
	Player[] players = new Player[5];
	List<RPGPlayer> rplayers = this.getList();
	for(int x = 0; x<rplayers.size(); x++)
	{
	    players[x] = rplayers.get(x).getPlayer();
	}
	return players;
    }
    public static void CreateParty(RPGPlayer partyLeader)
    {
	Party party = new Party(partyLeader);
	Parties.parties.put(partyLeader, party);
    }
    public void addPartyMember(RPGPlayer toAdd)
    {
	Parties.parties.put(toAdd, this);
	partyMembers.add(toAdd);
    }
    public void removeLeader()
    {
	partyMembers.remove(leader);
	Parties.parties.remove(leader);
	if(partyMembers.isEmpty())
	{
	    Parties.removeParty(this);
	}
	else{leader=partyMembers.getFirst();}
	this.sendMessage(leader.p.getName()+" is the new party leader!");
    }
    public void kickPlayer(RPGPlayer rp)
    {
	leader.p.sendMessage(ChatColor.BLUE+"You have kicked "+rp.p.getName()+" from your party!");
	this.sendMessage(rp.p.getName()+" was kicked from your party by "+leader.p.getName());
	rp.p.sendMessage(ChatColor.BLUE+leader.p.getName()+" has kicked you from the party!");
    }
    public void removePartyMember(RPGPlayer toRemove, String reason)
    {
	if(toRemove==leader){removeLeader();}
	else
	{
	    partyMembers.remove(toRemove);
	    Parties.parties.remove(toRemove);
	}
	if(reason.equalsIgnoreCase("kicked"))
	{
	    kickPlayer(toRemove);
	}
	if(reason.equalsIgnoreCase("left"))
	{
	    toRemove.p.sendMessage(ChatColor.BLUE+"You have left your party!");
	    this.sendMessage(toRemove.p.getName()+" Has left your party!");
	}
    }
    public int getPartySize(){return partyMembers.size();}
    public void sendMessage(String message)
    {
	for(RPGPlayer rp : partyMembers)
	{
	    if(rp!=null)rp.p.sendMessage(ChatColor.BLUE+message);
	}
    }
    public void sendMessage(String message, RPGPlayer sender)
    {
	String usernames="";
	for(RPGPlayer rp:this.getList())
	{
	    if(rp==leader){usernames+=rp.p.getName()+ChatColor.GOLD+" Party Leader"+"\n";}
	    else{usernames+=rp.p.getName()+"\n";}
	}

	message = ChatColor.AQUA+"[Party]\n"
		+ ChatColor.AQUA+"["+sender.p.getName()+"]:"+ChatColor.AQUA+message;
	String hoverText = usernames;
	IChatBaseComponent comp = constructComp(message, hoverText, "");
	sendToPlayers(comp,this.getPlayers());
    }
    public boolean isFull()
    {
	if(partyMembers.size()<5){return false;}
	else{Bukkit.broadcastMessage(partyMembers.size()+"SIZE");return true;}
    }
    public boolean isInParty(RPGPlayer player)
    {
	for(RPGPlayer rp : partyMembers)
	{
	    if(rp==player){return true;}
	}
	return false;
    }
    public boolean isLeader(RPGPlayer rp)
    {
	if(leader==rp){return true;}
	else{return false;}	
    }
    public void changeLeader(RPGPlayer newLeader)
    {
	leader=newLeader;
	this.sendMessage(newLeader.p.getName()+"is the new party leader!");
    }
    public void invitePlayer(final RPGPlayer invited)
    {
	if(!this.isInParty(invited))
	{
	    if(!this.isFull())
	    {
		if(!Parties.parties.containsKey(invited))
		{
		    invites.add(invited);
		    invited.p.sendMessage(ChatColor.BLUE+"You have been invited to join a party by "+this.leader.p.getName());
		    invited.invited=this;
		    new BukkitRunnable()
		    {
		    	public void run()
		    	{
		    	    Party.this.invites.remove(invited);
		    	    if(!Party.this.isInParty(invited))
		    	    {
		    		Party.this.leader.p.sendMessage(ChatColor.BLUE+invited.p.getName()+" Did not respond to your invite");
		    	    }
		    	}
		    
		    }.runTaskLater(BlackLance.getPlugin(), 300);
		}else{this.leader.p.sendMessage(ChatColor.BLUE+"Player is already in a party!");}
	    }else{this.leader.p.sendMessage(ChatColor.BLUE+"Your party is full!");}
	}else{this.leader.p.sendMessage(ChatColor.BLUE+"This player is already in your party");}
    }
    public boolean isInvited(RPGPlayer toCheck)
    {
	for(RPGPlayer rp : invites)
	{
	    if(rp==toCheck){return true;}
	}
	return false;
    }
    public void acceptInvite(RPGPlayer rp)
    {
	rp.invited=null;
	this.addPartyMember(rp);
	this.sendMessage(rp.p.getName()+" has joined your party!");
    }
    public void declineInvite(RPGPlayer rp){invites.remove(rp);rp.invited=null;}
    public void giveXP(double xp)
    {
	for(RPGPlayer rp : partyMembers)
	{
	    if(rp!=null)
	    {
		int levelDifference=rp.p.getLevel()-this.getAvgLevel();
		xp-=levelDifference*.15;
		if(xp>0){rp.addXP(xp-(levelDifference*.15));}
	    }
	}
    }
    public int getAvgLevel()
    {
	int average=0;
	for(RPGPlayer rp : partyMembers)
	{
	    if(rp!=null)
	    {
		average+=rp.p.getLevel();
	    }
	}
	return average/partyMembers.size();
    }
    public IChatBaseComponent constructComp(String message, String hoverText, String clickText)
    {
	IChatBaseComponent comp = new ChatMessage(message);
	ChatModifier modifier = new ChatModifier().setColor(EnumChatFormat.DARK_AQUA);
	modifier.setChatHoverable(new ChatHoverable(EnumHoverAction.SHOW_TEXT, new ChatMessage(hoverText)));
	modifier.setChatClickable(new ChatClickable(EnumClickAction.SUGGEST_COMMAND, clickText));
	comp.setChatModifier(modifier);
	return comp;
    }
    public static void sendToPlayers(IChatBaseComponent ic, Player...p)
    {
	PacketPlayOutChat packet = new PacketPlayOutChat(ic);
	for(Player onePlayer : p)
	{
	    ((CraftPlayer) onePlayer).getHandle().playerConnection.sendPacket(packet);
	}
    }
    
}
