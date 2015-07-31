package com.strongholdmc.blcore.playerclasses;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.strongholdmc.blcore.main.RPGStat;
import com.strongholdmc.blcore.main.RPGStatFinder;

public class RPGArmor
{
    static Pattern armorPattern = Pattern.compile("Defense: \\d+");
    HashMap<RPGStat, Integer> statValues = new HashMap<RPGStat, Integer>();
    
    public void addStat(RPGStat stat, int value)
    {
	statValues.put(stat, value);
    }

    public int getStatValue(RPGStat stat)
    {
	return statValues.get(stat);
    }
    
    public static void makeRPGArmor(ItemStack armor)
    {
	List<String> lore = armor.getItemMeta().getLore();
	RPGArmor a = new RPGArmor();
	for (String s : lore)
	{
	    for (RPGStatFinder stat : RPGStatFinder.values())
	    {
		int statValue = stat.tryGetValue(s);
		if (statValue != 0)
		{
		    a.addStat(RPGStat.getByName(stat.toString()), statValue);
		}
	    }
	}
	for (RPGStat stat : RPGStat.values())
	{
	    if (!a.statValues.containsKey(stat))
	    {
		a.statValues.put(stat, 0);
	    }
	}
	String serialStatString = RPGWeapon.convertToInvisibleString(a.statValues.get(RPGStat.LOW_DAMAGE) + ":" + a.statValues.get(RPGStat.HIGH_DAMAGE) + ":" + a.statValues.get(RPGStat.STRENGTH) + ":" + a.statValues.get(RPGStat.AGILITY) + ":" + a.statValues.get(RPGStat.INTELLECT) + ":" + a.statValues.get(RPGStat.VITALITY))+":"+a.statValues.get(RPGStat.DEFENSE);
	lore.set(lore.size() - 1, serialStatString);
	ItemMeta meta = armor.getItemMeta();
	meta.setLore(lore);
	armor.setItemMeta(meta);
	System.out.println("Added: " + serialStatString);
	RPGItems.addToMap(serialStatString, a);
    }

	public static String getSerialStatString(RPGArmor a)
	{
		return RPGWeapon.convertToInvisibleString(a.statValues.get(RPGStat.LOW_DAMAGE) + ":" + a.statValues.get(RPGStat.HIGH_DAMAGE) + ":" + a.statValues.get(RPGStat.STRENGTH) + ":" + a.statValues.get(RPGStat.AGILITY) + ":" + a.statValues.get(RPGStat.INTELLECT) + ":" + a.statValues.get(RPGStat.VITALITY))+":"+a.statValues.get(RPGStat.DEFENSE);
	}

	public static String getSerialStatString(ItemStack is)
	{
		return getSerialStatString(RPGArmor.getRPGArmor(is));
	}

	public static RPGArmor getRPGArmor(ItemStack is)
	{
		RPGArmor a = new RPGArmor();

		for (String s : is.getItemMeta().getLore())
		{
			for (RPGStatFinder stat : RPGStatFinder.values())
			{
				int statValue = stat.tryGetValue(s);
				if (statValue != 0)
				{
					a.addStat(RPGStat.getByName(stat.toString()), statValue);
				}
			}
		}
		for (RPGStat stat : RPGStat.values())
		{
			if (!a.statValues.containsKey(stat))
			{
				a.statValues.put(stat, 0);
			}
		}

		return a;
	}
    
    public static void addRPGArmor(ItemStack i)
    {
	RPGArmor a = new RPGArmor();
	String serialStatString = getSerializableString(i);
	String[] rawStats = serialStatString.replaceAll(ChatColor.COLOR_CHAR+"", "").split(":");
	a.addStat(RPGStat.LOW_DAMAGE, Integer.parseInt(rawStats[0]));
	a.addStat(RPGStat.HIGH_DAMAGE, Integer.parseInt(rawStats[1]));
	a.addStat(RPGStat.STRENGTH, Integer.parseInt(rawStats[2]));
	a.addStat(RPGStat.AGILITY, Integer.parseInt(rawStats[3]));
	a.addStat(RPGStat.INTELLECT, Integer.parseInt(rawStats[4]));
	a.addStat(RPGStat.VITALITY, Integer.parseInt(rawStats[5]));
	a.addStat(RPGStat.DEFENSE, Integer.parseInt(rawStats[6]));
	RPGItems.addToMap(serialStatString, a);
    }

	public static void addArmorLoreFromSerial(String serial, ItemStack is)
	{
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();

		lore.set(lore.size()-1, serial);
		im.setLore(lore);
	}
    
    public static void makeArmors(RPGPlayer rp)
    {
	for (ItemStack i : rp.getPlayer().getInventory().getContents())
	{
	    if (i == null)
	    {
		Bukkit.broadcastMessage("NULL");
		continue;
	    }
	    if (isArmor(i.getItemMeta().getLore()))
	    {
		if (isRPGArmor(i.getItemMeta().getLore()))
		{
		    Bukkit.broadcastMessage("GONNA ADD");
		    addRPGArmor(i);
		}
		else
		{
		    Bukkit.broadcastMessage("GONNA MAKE");
		    makeRPGArmor(i);
		}
	    }
	}
    }

    
    public static boolean isArmor(List<String> lore)
    {
	for(String s : lore)
	{
	    Matcher m = armorPattern.matcher(s);
	    if(m.find())
	    {
		return true;
	    }
	}
	return false;
    }
    
    public static boolean isRPGArmor(List<String> lore)
    {
	for(String s : lore)
	{
	    if(s.contains(ChatColor.COLOR_CHAR+""))
	    {
		return true;
	    }
	}
	return true;
    }

	public static String getSerializableString(ItemStack is)
	{
		List<String> lore = is.getItemMeta().getLore();
		if(isArmor(lore))
		{
			return lore.get(lore.size() - 1);
		}

		return null;
	}
}
