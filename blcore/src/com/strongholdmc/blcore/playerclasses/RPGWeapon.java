package com.strongholdmc.blcore.playerclasses;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.strongholdmc.blcore.main.RPGStat;
import com.strongholdmc.blcore.main.RPGStatFinder;
public class RPGWeapon
{
    private HashMap<RPGStat, Integer> statValues = new HashMap<RPGStat, Integer>();
    static Pattern p = Pattern.compile("\\d+-\\d+");

    public void addStat(RPGStat stat, int value)
    {
	statValues.put(stat, value);
    }

    public int getStatValue(RPGStat stat)
    {
	return statValues.get(stat);
    }

	public String getSerial()
	{
		return getSerialStatString(this);
	}

    public static void makeWeapons(RPGPlayer rp)
    {
	for (ItemStack i : rp.getPlayer().getInventory().getContents())
	{
	    if (i == null)
	    {
		continue;
	    }
	    if (isWeapon(i.getItemMeta().getLore()))
	    {
		if (isRPGWeapon(i.getItemMeta().getLore()))
		{
		    addWeapon(i);
		}
		else
		{
		    makeWeapon(i);
		}
	    }
	}
    }

    public static void makeWeapons(Player p)
    {
	for (ItemStack i : p.getInventory())
	{
	    if (isWeapon(i.getItemMeta().getLore()))
	    {
		if (isRPGWeapon(i.getItemMeta().getLore()))
		{
		    addWeapon(i);
		}
		else
		{
		    makeWeapon(i);
		}
	    }
	}
    }

    public static void addWeapon(ItemStack i)
    {
	List<String> lore = i.getItemMeta().getLore();
	RPGWeapon w = new RPGWeapon();
	String serialStatString = lore.get(lore.size() - 1);
	String[] rawStats = serialStatString.replaceAll(ChatColor.COLOR_CHAR+"", "").split(":");
	w.addStat(RPGStat.LOW_DAMAGE, Integer.parseInt(rawStats[0]));
	w.addStat(RPGStat.HIGH_DAMAGE, Integer.parseInt(rawStats[1]));
	w.addStat(RPGStat.STRENGTH, Integer.parseInt(rawStats[2]));
	w.addStat(RPGStat.AGILITY, Integer.parseInt(rawStats[3]));
	w.addStat(RPGStat.INTELLECT, Integer.parseInt(rawStats[4]));
	w.addStat(RPGStat.VITALITY, Integer.parseInt(rawStats[5]));
	w.addStat(RPGStat.DEFENSE, Integer.parseInt(rawStats[6]));
	RPGItems.addToMap(serialStatString, w);
    }

    public static void makeWeapon(ItemStack weapon)
    {
	List<String> lore = weapon.getItemMeta().getLore();
	RPGWeapon w = new RPGWeapon();
	for (String s : lore)
	{
	    for (RPGStatFinder stat : RPGStatFinder.values())
	    {
		int statValue = stat.tryGetValue(s);
		if (statValue != 0)
		{
		    w.addStat(RPGStat.getByName(stat.toString()), statValue);
		}
	    }
	}
	for (RPGStat stat : RPGStat.values())
	{
	    if (!w.statValues.containsKey(stat))
	    {
		w.statValues.put(stat, 0);
	    }
	}
	String serialStatString = getSerialStatString(w);
	lore.set(lore.size() - 1, serialStatString);
	ItemMeta meta = weapon.getItemMeta();
	meta.setLore(lore);
	weapon.setItemMeta(meta);
	System.out.println("Added: " + serialStatString);
	RPGItems.addToMap(serialStatString, w);
    }

	public static String getSerialStatString(RPGWeapon w)
	{
		return convertToInvisibleString(w.statValues.get(RPGStat.LOW_DAMAGE) + ":" + w.statValues.get(RPGStat.HIGH_DAMAGE) + ":" + w.statValues.get(RPGStat.STRENGTH) + ":" + w.statValues.get(RPGStat.AGILITY) + ":" + w.statValues.get(RPGStat.INTELLECT) + ":" + w.statValues.get(RPGStat.VITALITY))+":"+w.statValues.get(RPGStat.DEFENSE);
	}

	public static String getSerialStatString(ItemStack is)
	{
		return getSerialStatString(RPGWeapon.getRPGWeapon(is));
	}

    public static String convertToInvisibleString(String s)
    {
	String hidden = "";
	for (char c : s.toCharArray())
	    hidden += ChatColor.COLOR_CHAR + "" + c;
	return hidden;
    }

	public static void addWeaponLoreFromSerial(String serial, ItemStack is)
	{
		ItemMeta im = is.getItemMeta();
		List<String> lore = im.getLore();

		lore.set(lore.size()-1, serial);
		im.setLore(lore);
	}

	public static RPGWeapon getRPGWeapon(ItemStack is)
	{
		RPGWeapon w = new RPGWeapon();

		for (String s : is.getItemMeta().getLore())
		{
			for (RPGStatFinder stat : RPGStatFinder.values())
			{
				int statValue = stat.tryGetValue(s);
				if (statValue != 0)
				{
					w.addStat(RPGStat.getByName(stat.toString()), statValue);
				}
			}
		}
		for (RPGStat stat : RPGStat.values())
		{
			if (!w.statValues.containsKey(stat))
			{
				w.statValues.put(stat, 0);
			}
		}

		return w;
	}

    public static boolean isWeapon(List<String> lore)
    {
	for (String s : lore)
	{
	    if (isWeapon(s))
	    {
		Bukkit.broadcastMessage("ITS A WEAPON MORTY");
		return true;
	    }
	    Bukkit.broadcastMessage("SHITS NOT A WEAPON MORTY!");
	}
	return false;
    }

    public static boolean isWeapon(String lore)
    {
	Matcher m = p.matcher(lore);
	if (m.find())
	{
	    Bukkit.broadcastMessage("ITS A WEAPON MORTY");
	    return true;
	}
	Bukkit.broadcastMessage("SHITS NOT A WEAPON MORTY!");
	return false;
    }

    public static boolean isRPGWeapon(List<String> lore)
    {
	for (String s : lore)
	{
	    if (isRPGWeapon(s))
	    {
		return true;
	    }
	}
	return false;
    }

    public static boolean isRPGWeapon(String lore)
    {
	if (lore.toString().contains(ChatColor.COLOR_CHAR + ""))
	{
	    return true;
	}
	return false;
    }

}
