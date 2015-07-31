package com.strongholdmc.blpvp.util;

import org.bukkit.Bukkit;
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
		blm.getNPC().getBukkitEntity().setCustomName("§r" + barArray[roundUpPositiveWithMax(((health / max) * 20.0), 20)]);
		// check for visibility
	    }
	});
    }

    public static String[] getDefaultsBars()
    {
	String[] barArray = new String[21];
	barArray[0] = "Â§c|Â§7|||||||||||||||||||";
	barArray[1] = "Â§c|Â§7|||||||||||||||||||";
	barArray[2] = "Â§c||Â§7||||||||||||||||||";
	barArray[3] = "Â§c|||Â§7|||||||||||||||||";
	barArray[4] = "Â§c||||Â§7||||||||||||||||";
	barArray[5] = "Â§e|||||Â§7|||||||||||||||";
	barArray[6] = "Â§e||||||Â§7||||||||||||||";
	barArray[7] = "Â§e|||||||Â§7|||||||||||||";
	barArray[8] = "Â§e||||||||Â§7||||||||||||";
	barArray[9] = "Â§e|||||||||Â§7|||||||||||";
	barArray[10] = "Â§e||||||||||Â§7||||||||||";
	barArray[11] = "Â§a|||||||||||Â§7|||||||||";
	barArray[12] = "Â§a||||||||||||Â§7||||||||";
	barArray[13] = "Â§a|||||||||||||Â§7|||||||";
	barArray[14] = "Â§a||||||||||||||Â§7||||||";
	barArray[15] = "Â§a|||||||||||||||Â§7|||||";
	barArray[16] = "Â§a||||||||||||||||Â§7||||";
	barArray[17] = "Â§a|||||||||||||||||Â§7|||";
	barArray[18] = "Â§a||||||||||||||||||Â§7||";
	barArray[19] = "Â§a|||||||||||||||||||Â§7|";
	barArray[20] = "Â§a||||||||||||||||||||";
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
