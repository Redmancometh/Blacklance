package com.strongholdmc.blcore.playerclasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.strongholdmc.blcore.main.RPGStat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.inventivetalent.bossbar.BossBarAPI;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.User;
import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blcore.party.Party;
import com.strongholdmc.blcore.storage.DBUtil;
import com.strongholdmc.blcore.storage.RPGPlayers;

public class RPGPlayer
{
	private int xp;
	private int health;
	private int maxhealth;
	private int uid;
	@SuppressWarnings("unused")
	private int baseStr;
	@SuppressWarnings("unused")
	private int baseAgi;
	@SuppressWarnings("unused")
	private int baseInt;
	@SuppressWarnings("unused")
	private int baseVit;

	// How much money the player has
	private int money;

	// Modified Stats
	private Map<RPGStat, Integer> modStats = new HashMap<RPGStat, Integer>();

	// private List<ActiveQuest> activeQuests;
	// private List<String> finishedQuests;

	// Yo
	public Player p;
	public Party invited;
	public BukkitTask regenTask;
	private Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

	public RPGPlayer(Player p, int uid, int xp, int health, int maxhealth)
	{
		this.p = p;
		this.uid = uid;
		this.xp = xp;
		this.health = health;
		this.maxhealth = maxhealth;

		for (RPGStat modStat : RPGStat.values())
		{
			modStats.put(modStat, 0);
		}
	}

	public void decrementModStat(RPGStat stat, int amount)
	{
		setModStat(stat, modStats.get(stat) - amount);
	}

	public void incrementModStat(RPGStat stat, int amount)
	{
		setModStat(stat, modStats.get(stat) + amount);
	}

	public void setModStat(RPGStat stat, int amount)
	{
		modStats.put(stat, amount);
	}

	public int getModStat(RPGStat stat)
	{
		return modStats.get(stat);
	}

	public Map<RPGStat, Integer> getModStats()
	{
		return new HashMap<RPGStat, Integer>(modStats);
	}

	public int getXP()
	{
		return xp;
	}

	public int getHealth()
	{
		return health;
	}

	public int getMaxHealth()
	{
		return maxhealth;
	}

	public void addXP(double d)
	{
		this.xp += d;
		if (this.getXP() >= 100)
		{
			float cexp = (p.getExp() * p.getExpToLevel());
			while (this.getXP() >= 100)
			{
				if (p.getExpToLevel() - cexp == 1)
				{
					p.sendMessage(ChatColor.GOLD + "Congratulations on level " + (p.getLevel() + 1) + "!");
					this.setMaxHealth(p, false);
					String healthdisplay = ChatColor.DARK_GREEN + "Health:  " + ChatColor.DARK_RED + this.getHealth() + "/" + this.getMaxHealth();
					BossBarAPI.setMessage(p, healthdisplay, (Math.abs(this.getHealth() / this.getMaxHealth()) * 100));
				}
				p.giveExp(1);
				this.setXP((int) (this.getXP() - 100));
			}
		}
	}

	public void setXP(int d)
	{
		this.xp = d;
	}

	public void setMaxHealth(final Player p, final boolean levelup)
	{
		BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
		scheduler.scheduleSyncDelayedTask(BlackLance.pl, new Runnable()
		{
			public void run()
			{
				maxhealth = ((p.getLevel() * 10) + 30);
				if (levelup)
				{
					health = maxhealth;
				}
			}
		});
	}

	public void healPlayer(double d, Player p)
	{
		if (health + d < maxhealth)
		{
			this.health += d;
		}
		else
		{
			this.health = this.maxhealth;
		}
	}

	@SuppressWarnings("deprecation")
	public void damagePlayer(int oldDamage)
	{
		CompletableFuture.runAsync(() ->
		{
			int damage = oldDamage;
			p.playEffect(EntityEffect.HURT);
			if (this.health - damage <= 0)
			{
				this.health = 0;
				new BukkitRunnable()
				{
					public void run()
					{
						p.setHealth(0);
					}
				}.runTask(BlackLance.getPlugin());
				p.setHealth(0);
			}
			else if (damage <= 0)
			{
				damage = 2;
				this.health -= 2;
			}
			else
			{
				this.health -= damage;
			}
			p.sendMessage(ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Combat" + ChatColor.DARK_GRAY + "] " + ChatColor.GOLD + "You have been hit for " + damage + " damage");
			p.setHealth(Math.abs((double) health / (double) maxhealth * 20));
			String healthdisplay = ChatColor.DARK_GREEN + "Health:  " + ChatColor.DARK_RED + this.getHealth() + "/" + this.getMaxHealth();
			BossBarAPI.setMessage(p, healthdisplay, (Math.abs(this.getHealth() / this.getMaxHealth()) * 100));
			if (health <= 0)
			{
				new BukkitRunnable()
				{
					public void run()
					{
						p.setHealth(0);
					}
				}.runTask(BlackLance.getPlugin());
			}
		});
	}

	public User getUser(Player p)
	{
		User u = ess.getUser(p);
		return u;
	}

	public Player getPlayer()
	{
		return this.p;
	}

	public int getUID()
	{
		return this.uid;
	}

	public static RPGPlayer createRPGPLayer(ResultSet rs, Player p) throws SQLException
	{
		int id = 0;
		int health = 30;
		int maxhealth = 30;
		int exp = 0;
		while (rs.next())
		{
			id = rs.getInt(2);
			health = rs.getInt(3);
			maxhealth = rs.getInt(4);
			exp = rs.getInt(5);
			return new RPGPlayer(p, id, exp, health, maxhealth);
		}
		return null;
	}

	public void scheduleHeals()
	{
		regenTask = new BukkitRunnable()
		{
			public void run()
			{
				if (!p.isDead())
				{
					RPGPlayer.this.healPlayer(((p.getLevel()) + 2), p);
					p.setHealth((double) RPGPlayer.this.getHealth() / (double) RPGPlayer.this.getMaxHealth() * 20);
					String healthdisplay = ChatColor.DARK_GREEN + "Health:  " + ChatColor.DARK_RED + RPGPlayer.this.getHealth() + "/" + RPGPlayer.this.getMaxHealth();
					BossBarAPI.setMessage(p, healthdisplay, (RPGPlayer.this.getHealth() / RPGPlayer.this.getMaxHealth()) * 100);
				}
			}
		}.runTaskTimerAsynchronously(BlackLance.pl, 10, 50);
	}

	public void cancelHeals()
	{
		this.regenTask.cancel();
	}

	/*
	 * public List<String> getFinishedQuests() { return new
	 * ArrayList<String>(finishedQuests); }
	 * 
	 * public boolean isQuestFinished(String questName) { return
	 * finishedQuests.contains(questName); }
	 * 
	 * public boolean isQuestFinished(Quest quest) { return
	 * isQuestFinished(quest.getName()); }
	 * 
	 * public List<ActiveQuest> getActiveQuests() { return activeQuests; }
	 * 
	 * public boolean isActiveQuest(ActiveQuest activeQuest) { return
	 * isActiveQuest(activeQuest.getName()); }
	 * 
	 * public boolean isActiveQuest(Quest quest) { return
	 * isActiveQuest(quest.getName()); }
	 * 
	 * public boolean isActiveQuest(String questName) { for(ActiveQuest
	 * activeQuest : activeQuests) { if(activeQuest.getName().equals(questName))
	 * { return true; } }
	 * 
	 * return false; }
	 * 
	 * public void addFinishedQuest(String questName) {
	 * finishedQuests.add(questName); }
	 * 
	 * public void addFinishedQuest(Quest quest) {
	 * addFinishedQuest(quest.getName()); }
	 * 
	 * public void addActiveQuest(ActiveQuest activeQuest) {
	 * activeQuests.add(activeQuest); }
	 */

	public void addMoney(int amount)
	{
		money += amount;
	}

	public void removeMoney(int amount)
	{
		if (amount >= money)
		{
			money = 0;
		}
		else
		{
			money -= amount;
		}
	}

	public void setMoney(int amount)
	{
		if (amount >= 0)
		{
			money = 0;
		}
		else
		{
			money = amount;
		}
	}

	public int getMoney()
	{
		return money;
	}
}
