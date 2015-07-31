package com.strongholdmc.blpvp.util;
import java.util.Random;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.strongholdmc.blcore.storage.RPGPlayers;
import com.strongholdmc.blmobs.trait.blm;
public class CombatUtil
{
    static Random r = new Random();
    public static int level;

    public static double generateHit(Player phitter)
    {
	if (phitter.getItemInHand() != null && phitter.getItemInHand().hasItemMeta() && phitter.getItemInHand().getItemMeta().hasLore())
	{
	    if (phitter.getItemInHand().getItemMeta().getLore().toString().contains("Damage"))
	    {
		int[] damages = RPGPlayers.getRPGPlayer(phitter).getHitDamage(phitter.getItemInHand());
		double Hit = (r.nextInt(damages[1]) + damages[0]) + (phitter.getLevel() * 1.25) + 2;
		return Hit;
	    }
	    else
	    {
		phitter.sendMessage(ChatColor.DARK_RED + "Use a weapon to attack.");
		return 0;
	    }
	}
	else
	{
	    phitter.sendMessage(ChatColor.DARK_RED + "Use a weapon to attack.");
	    return 0;
	}
    }

    public static double getAbsorption(Player phit)
    {
	double absorb = 0;
	if (phit.getLevel() < 25)
	{
	    absorb = phit.getLevel() * .45;
	}
	else
	{
	    absorb = (phit.getLevel() * .1);
	}
	if (phit.getEquipment().getBoots() != null && phit.getEquipment().getBoots().hasItemMeta())
	{
	    String boots = phit.getEquipment().getBoots().getItemMeta().getLore().toString();
	    absorb += Character.getNumericValue(boots.charAt(10));
	}
	if (phit.getEquipment().getHelmet() != null && phit.getEquipment().getHelmet().hasItemMeta())
	{
	    String helmet = phit.getEquipment().getHelmet().getItemMeta().getLore().toString();
	    absorb += Character.getNumericValue(helmet.charAt(10));
	}
	if (phit.getEquipment().getLeggings() != null && phit.getEquipment().getLeggings().hasItemMeta())
	{
	    String leggings = phit.getEquipment().getLeggings().getItemMeta().getLore().toString();
	    absorb += Character.getNumericValue(leggings.charAt(10));
	}
	if (phit.getEquipment().getChestplate() != null && phit.getEquipment().getChestplate().hasItemMeta())
	{
	    String chestplate = phit.getEquipment().getChestplate().getItemMeta().getLore().toString();
	    absorb += Character.getNumericValue(chestplate.charAt(10));
	}
	return absorb * .9;
    }

    public static double getmobDamage(NPC npc)
    {
	level = getLevel(npc);
	return (((level * 2.7) + r.nextInt(level + 3)) + 2);
    }

    public static double getArrowDamage(NPC paul)
    {
	int level = getLevel(paul);
	return ((level * 1.5) + 2);
    }

    public static double getmobAbsorb(NPC paul)
    {
	double absorption = 0;
	int level = getLevel(paul);
	// Change to switch later -Redman
	if (level <= 20)
	{
	    absorption = ((level * 2.3));
	}
	return absorption;
    }

    public static int getLevel(NPC paul)
    {
	if (paul.isSpawned())
	{
	    if (paul.hasTrait(blm.class))
	    {
		blm blminstance = paul.getTrait(blm.class);
		level = blminstance.getLevel();
	    }
	}
	return level;
    }

}
