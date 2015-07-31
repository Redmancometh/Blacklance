package com.strongholdmc.blcore.storage;

import java.util.Arrays;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;

public class DataSetter
{
    private JavaPlugin blacklance;
    private FileConfiguration config;
    private int questID;
    List<String> quests = Arrays.asList("Hello World", "Welcome to Bukkit", "Have a Good Day!");

    public DataSetter(JavaPlugin blacklance)
    {
	this.blacklance = blacklance;
	this.config = blacklance.getConfig();
    }

    public void initialAdd(Player p)
    {
	config.createSection("Quests." + p.getName());
	config.createSection("Quests." + p.getName() + ".QIP");
	config.createSection("Quests." + p.getName() + ".QC");
	blacklance.saveConfig();
    }

    public void completeQuest(int QuestID, Player p)
    {
	this.questID = QuestID;
	p.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
	config.createSection("Quests." + p.getName() + ".QC." + questID);
    }

    public void addQIP(int questID, Player p)
    {
	config.createSection("Quests." + p.getName() + ".QIP." + questID);
    }
}
