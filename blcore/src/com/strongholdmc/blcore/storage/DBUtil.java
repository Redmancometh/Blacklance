package com.strongholdmc.blcore.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blcore.playerclasses.RPGPlayer;

public class DBUtil
{
	public static void setup(Connection c) throws SQLException
	{
		Statement statement = c.createStatement();
		statement.execute("CREATE TABLE Parties(uid1 int, uid2 int, uid3 int, uid4 int, uid5 int, partyid int primary key NOT NULL AUTO_INCREMENT);");
		statement.execute("CREATE TABLE PlayerItems(playerid INT, mindmg int, maxdmg int, name varchar(60), itemid int primary key NOT NULL AUTO_INCREMENT);");
		statement.execute("CREATE TABLE Players(UUID varchar(50), uid bigint primary key NOT NULL AUTO_INCREMENT, charname varchar(40), health INT, maxhealth INT, exp INT, x INT, y INT, z INT, partyID int, str int, agi int, intel int, vit int);");
		statement.execute("CREATE TABLE Quests(questID int, uid int, progress int, completed int);");
		statement.execute("CREATE TABLE Resources(type varchar(25), x DECIMAL, y DECIMAL, z DECIMAL, indexer INT primary key NOT NULL AUTO_INCREMENT);");
	}

	public static ResultSet getProgress(int uid, int questID) throws SQLException
	{
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("SELECT progress, completed FROM Quests where uid=?");
		ps.setInt(1, uid);
		ResultSet rs = ps.executeQuery();
		return rs;
	}

	public static void setProgress(int uid, int questID, int progress) throws SQLException
	{
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("UPDATE Quests SET progress=? WHERE uid=? AND questid=?");
		ps.setInt(1, progress);
		ps.setInt(2, uid);
		ps.setInt(3, questID);
		ps.execute();
	}

	public static void setComplete(int uid, int questID, int complete) throws SQLException
	{
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("UPDATE Quests SET completed=? WHERE uid=? AND questid=?");
		ps.setInt(1, complete);
		ps.setInt(2, uid);
		ps.setInt(3, questID);
		ps.execute();
	}

	public static int getComplete(int uid, int questID) throws SQLException
	{
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("SELECT completed FROM Quests WHERE uid=? AND questid=?");
		ps.setInt(1, uid);
		ps.setInt(2, questID);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			return rs.getInt(1);
		}
		return 3;
	}

	public static void addCharacter(RPGPlayer p) throws SQLException
	{
		String uuid = p.getPlayer().getUniqueId().toString();
		Location loc = p.getPlayer().getLocation();
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("INSERT INTO Players(UUID,health,maxhealth,exp,x,y,z) VALUES (?,?,?,?,?,?,?)");
		ps.setString(1, uuid);
		ps.setInt(2, p.getHealth());
		ps.setInt(3, p.getMaxHealth());
		ps.setInt(4, p.getXP());
		ps.setDouble(5, loc.getX());
		ps.setDouble(6, loc.getY());
		ps.setDouble(7, loc.getZ());
		ps.execute();
	}

	public static boolean doesPlayerExist(Player p) throws SQLException
	{
		String uuid = p.getUniqueId().toString();
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("SELECT uid FROM Players where UUID=?");
		ps.setString(1, uuid);
		ResultSet rs = ps.executeQuery();
		if (rs.next())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public static int getUID(Player p) throws SQLException
	{
		String uuid = p.getUniqueId().toString();
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("SELECT uid FROM Players where UUID=?");
		ps.setString(1, uuid);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			return rs.getInt(1);
		}
		return 0;
	}

	public static int getPlayerMaxHealth(int uid) throws SQLException
	{
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("SELECT maxhealth FROM Players where uid=?");
		ps.setInt(1, uid);
		return ps.executeQuery().getInt(1);
	}

	public static int getPlayerHealth(int uid) throws SQLException
	{
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("SELECT health FROM Players where uid=?");
		ps.setInt(1, uid);
		return ps.executeQuery().getInt(1);
	}

	public static int getPlayerXP(int uid) throws SQLException
	{
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("SELECT exp FROM Players where uid=?");
		ps.setInt(1, uid);
		return ps.executeQuery().getInt(1);
	}

	public static ResultSet getPlayerData(Player p) throws SQLException
	{
		String uuid = p.getUniqueId().toString();
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("SELECT * FROM Players where uuid=?");
		ps.setString(1, uuid);
		return ps.executeQuery();
	}

	public static Location getLocation(Connection c, Player p) throws SQLException
	{
		List<Integer> locData = new ArrayList<Integer>();
		String uuid = p.getUniqueId().toString();
		Statement statement = BlackLance.getConnection().createStatement();
		locData.add(0, (statement.executeQuery("SELECT x from Players where uuid=" + uuid).getInt(0)));
		locData.add(1, (statement.executeQuery("SELECT y from Players where uuid=" + uuid).getInt(0)));
		locData.add(2, (statement.executeQuery("SELECT z from Players where uuid=" + uuid).getInt(0)));
		return new Location(p.getWorld(), locData.get(0), locData.get(1), locData.get(2));
	}

	public static void saveDataByID(int uid, RPGPlayer rp) throws SQLException
	{
		Location loc1 = rp.getPlayer().getLocation();
		int x = (int) loc1.getX();
		int y = (int) loc1.getY();
		int z = (int) loc1.getZ();
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("UPDATE Players SET exp=?, health=?, maxhealth=?, x=?, y=?, z=? WHERE uid=?");
		ps.setInt(1, rp.getXP());
		ps.setInt(2, rp.getHealth());
		ps.setInt(3, rp.getMaxHealth());
		ps.setInt(4, x);
		ps.setInt(5, y);
		ps.setInt(6, z);
		ps.setInt(7, rp.getUID());
		ps.execute();
	}

	public static void addResourceNode(String type, Location loc) throws SQLException
	{
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("INSERT into Resources(type,x,y,z) VALUES(?,?,?,?)");
		ps.setString(1, type);
		ps.setDouble(2, loc.getX());
		ps.setDouble(3, loc.getY());
		ps.setDouble(4, loc.getZ());
		ps.execute();
	}

	public static void removeResourceNode(Location loc) throws SQLException
	{
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("DELETE FROM Resources where x=?, y=?, z=?");
		ps.setDouble(1, loc.getX());
		ps.setDouble(2, loc.getY());
		ps.setDouble(3, loc.getZ());
		ps.execute();
	}

	public static ResultSet getResourceNodes(String type) throws SQLException
	{
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("SELECT x,y,z FROM Resources where type=?");
		ps.setString(1, type);
		return ps.executeQuery();
	}

	public static ResultSet getAllResourceNodes() throws SQLException
	{
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("SELECT x,y,z,type FROM Resources");
		return ps.executeQuery();
	}

	public static int getWeaponData(String itemname, int mindmg, int maxdmg, int PlayerID) throws SQLException
	{
		int uid = 0;
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("SELECT itemid FROM PlayerItems where name=? AND mindmg=? AND maxdmg=? AND PlayerID=?");
		ps.setString(1, itemname);
		ps.setInt(2, mindmg);
		ps.setInt(3, maxdmg);
		ps.setInt(4, PlayerID);
		ResultSet rs = ps.executeQuery();
		while (rs.next())
		{
			uid = rs.getInt(1);
		}
		return uid;
	}

	public static void setWeaponData(String ItemName, int mindmg, int maxdmg, int PlayerID) throws SQLException
	{
		PreparedStatement ps = null;
		ps = BlackLance.getConnection().prepareStatement("INSERT INTO  PlayerItems(playerid, mindmg, maxdmg, name) VALUES(?,?,?,?);");
		ps.setInt(1, PlayerID);
		ps.setInt(2, mindmg);
		ps.setInt(3, maxdmg);
		ps.setString(4, ItemName);
		ps.execute();
	}
}
