package com.strongholdmc.blmobs.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blmobs.trait.blm;

public class BarUtil
{
    public static void showMobHealthBar(blm blm)
    {
	Bukkit.getScheduler().scheduleSyncDelayedTask(BlackLance.getPlugin(), new Runnable()
	{
	    String[] barArray = getDefaultsBars();
	    @SuppressWarnings("deprecation")
	    public void run()
	    {
		// check for compatibility
		double health = blm.getHealth();
		double max = blm.getmHealth();
		// if the health is 0
		if (health <= 0.0)
		{
		    return;
		}
		blm.getNPC().getBukkitEntity().setCustomName(barArray[roundUpPositiveWithMax(((health / max) * 20.0), 20)]);
		// check for visibility
	    }
	});
    }

    public static String[] getDefaultsBars()
    {
	String[] barArray = new String[21];
	barArray[0] = "§c|"+ChatColor.DARK_GRAY+"|||||||||||||||||||";
	barArray[1] = "§c|"+ChatColor.DARK_GRAY+"|||||||||||||||||||";
	barArray[2] = "§c||"+ChatColor.DARK_GRAY+"||||||||||||||||||";
	barArray[3] = "§c|||"+ChatColor.DARK_GRAY+"|||||||||||||||||";
	barArray[4] = "§c||||"+ChatColor.DARK_GRAY+"||||||||||||||||";
	barArray[5] = "§e|||||"+ChatColor.DARK_GRAY+"|||||||||||||||";
	barArray[6] = "§e||||||"+ChatColor.DARK_GRAY+"||||||||||||||";
	barArray[7] = "§e|||||||"+ChatColor.DARK_GRAY+"|||||||||||||";
	barArray[8] = "§e||||||||"+ChatColor.DARK_GRAY+"||||||||||||";
	barArray[9] = "§e|||||||||"+ChatColor.DARK_GRAY+"|||||||||||";
	barArray[10] = "§e||||||||||"+ChatColor.DARK_GRAY+"||||||||||";
	barArray[11] = "§a|||||||||||"+ChatColor.DARK_GRAY+"|||||||||";
	barArray[12] = "§a||||||||||||"+ChatColor.DARK_GRAY+"||||||||";
	barArray[13] = "§a|||||||||||||"+ChatColor.DARK_GRAY+"|||||||";
	barArray[14] = "§a||||||||||||||"+ChatColor.DARK_GRAY+"||||||";
	barArray[15] = "§a|||||||||||||||"+ChatColor.DARK_GRAY+"|||||";
	barArray[16] = "§a||||||||||||||||"+ChatColor.DARK_GRAY+"||||";
	barArray[17] = "§a|||||||||||||||||"+ChatColor.DARK_GRAY+"|||";
	barArray[18] = "§a||||||||||||||||||"+ChatColor.DARK_GRAY+"||";
	barArray[19] = "§a|||||||||||||||||||"+ChatColor.DARK_GRAY+"|";
	barArray[20] = "§a||||||||||||||||||||";
	return barArray;
    }

    public static int roundUpPositiveWithMax(double d, int max)
    {
	int result = roundUpPositive(d);
	if (d > max)
	    return max;
	return result;
    }

    public static int roundUpPositive(double d)
    {
	int i = (int) d;
	double remainder = d - i;
	if (remainder > 0.0)
	{
	    i++;
	}
	if (i < 0)
	    return 0;
	return i;
    }
}
