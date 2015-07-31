package com.strongholdmc.blmobs.trait;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import com.strongholdmc.blcore.party.Parties;
import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blcore.storage.RPGPlayers;
import com.strongholdmc.blevents.combatevents.DamageNPCEvent;
import com.strongholdmc.blevents.combatevents.MobDeathEvent;
import com.strongholdmc.blmobs.goals.AggressiveWanderGoal;
import com.strongholdmc.blmobs.goals.PassiveWanderGoal;
import com.strongholdmc.blmobs.util.BarUtil;
import com.strongholdmc.blmobs.util.DropUtil;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.ai.Goal;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;

public class blm extends Trait
{
    DataKey key;
    private int level;
    private double health;
    private int maxhealth;
    private boolean dead;
    private boolean aggressive;
    private RPGPlayer target;
    private Location aggroLoc;
    private int uid;
    public blm()
    {
	super("blm");
    }

    public RPGPlayer getTarget()
    {
	return target;
    }
    
    public void setAggroLoc(Location l)
    {
	this.aggroLoc=l;
    }
    
    public Location getAggroLoc()
    {
	return this.aggroLoc;
    }
    
    public void setTarget(RPGPlayer rp)
    {
	this.target=rp;
    }
    
    public void setTarget(Player p)
    {
	setTarget(RPGPlayers.getRPGPlayer(p));
    }
    
    public void load(DataKey key)
    {
	aggressive = key.getBoolean("aggressive");
	level = key.getInt("level");
	if (level < 5)
	{
	    this.maxhealth = (level * 15) + 25;
	    health = maxhealth;
	}
	else
	{
	    this.maxhealth = (level * 20) + 40;
	    health = maxhealth;
	}
    }

    public void save(DataKey key)
    {
	key.setInt("level", this.level);
	key.setDouble("health", this.maxhealth);
	key.setBoolean("aggressive", this.aggressive);
    }

    public void onSpawn()
    {
	if (level < 5)
	{
	    this.maxhealth = (level * 15) + 25;
	    health = maxhealth;
	}
	else
	{
	    this.maxhealth = (level * 20) + 40;
	    health = maxhealth;
	}
	this.health = this.maxhealth;
	Goal goal;
	if(isAggressive())
	{
	    Bukkit.broadcastMessage("BE AGGRESSIVE");
	    goal = AggressiveWanderGoal.createWithNPCAndRange(this.npc, 4);
	}
	else
	{
	    Bukkit.broadcastMessage("BE PASSIVE");
	    goal = PassiveWanderGoal.createWithNPCAndRange(this.npc, 4);
	}
	CitizensAPI.registerEvents(goal);
	this.npc.getDefaultGoalController().addGoal(goal, 1);
	// BLReflectUtil.getGoalSelector(this.npc.getBukkitEntity());
    }

    public boolean isAggressive()
    {
	return this.aggressive;
    }

    public int getLevel()
    {
	return this.level;
    }

    public double getHealth()
    {
	return health;
    }

    @SuppressWarnings("deprecation")
    public Entity getBukkitEntity()
    {
	return this.npc.getBukkitEntity();
    }

    public int getmHealth()
    {
	return maxhealth;
    }

    public void setLevel(int level)
    {
	this.level = level;
	if (level < 5)
	{
	    this.maxhealth = (level * 15) + 25;
	    health = maxhealth;
	}
	else
	{
	    this.maxhealth = (level * 20) + 40;
	    health = maxhealth;
	}
    }

    public void setAggressive(boolean aggressive)
    {
	this.aggressive = aggressive;
    }

    public void setHealth(int health)
    {
	this.health = health;
    }

    @SuppressWarnings("deprecation")
    public void removeMobHealth(double removeHealth, final Player killer)
    {
	if (!this.npc.isSpawned())
	{
	    return;
	}
	this.health -= removeHealth;
	if (this.health <= 0 && !this.dead)
	{
	    this.dead = true;
	    RPGPlayer rp = RPGPlayers.getRPGPlayer(killer);
	    DropUtil du = new DropUtil();
	    du.dropDecider(this.level, killer, this.npc.getBukkitEntity());
	    double differenceXP = (level * 2 + 20) * ((killer.getLevel() - this.level) * .20);
	    double xp = (level * 2 + 30) - differenceXP;
	    if (xp > 0)
	    {
		if (Parties.parties.containsKey(rp))
		{
		    Parties.parties.get(rp).giveXP(xp);
		}
		else
		{
		    rp.addXP(xp);
		}
	    }
	    Bukkit.getPluginManager().callEvent(new MobDeathEvent(killer, this.npc, xp));
	}
	else
	{
	    Bukkit.getPluginManager().callEvent(new DamageNPCEvent(killer, this.npc, removeHealth, this.npc.getBukkitEntity().getLocation()));
	    this.npc.getBukkitEntity().playEffect(EntityEffect.HURT);
	    BarUtil.showMobHealthBar(this);
	    return;
	}
    }

    public void addHealth(int addhealth)
    {
	this.health += addhealth;
    }

    public int getUid()
    {
	return uid;
    }

    public void setUid(int uid)
    {
	this.uid = uid;
    }

}
