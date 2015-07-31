package com.strongholdmc.blmobs.util;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.scheduler.BukkitRunnable;

import com.strongholdmc.blcore.main.BlackLance;
public class DropUtil implements Listener
{
    private int hdmg;
    private int lowdmg;
    private int armwep;
    private int defense;
    private int level;
    private double type;
    private List<String> lore = new ArrayList<String>();
    private String prefix = "";
    private String suffix = "";
    private String value = "Sell Value: ";
    private ItemStack drop = new ItemStack(Material.WOOD);
    private ItemMeta dropmeta = drop.getItemMeta();
    private EntityDeathEvent event;
    private boolean armor = false;
    private Random t = new Random();
    public void clear(EntityDeathEvent event)
    {
	event.getDrops().clear();
	event.getEntity().getEquipment().clear();
	event.setDroppedExp(0);
    }

    public double dropDecider(int level, Player killer, Entity paul)
    {
	this.level = level;
	double doDrop = Math.random();
	if (doDrop < (.99))
	{
	    double dropWheel = Math.random();
	    if (dropWheel < (.55))
	    {
		dropCommon(level, killer, paul);
	    }
	    else if (dropWheel >= (.55) && dropWheel <= (.95))
	    {
		dropUncommon(level, killer, paul);
	    }
	    else if (dropWheel > (.95) && dropWheel < (98))
	    {
		dropRare(level, killer, paul);
	    }
	    else if (dropWheel >= (.98) && dropWheel <= (1))
	    {
		this.dropEpic(level, event);
	    }
	    /* if(dropWheel>(.98)){this.dropLegendary(level, event);} */
	}
	if (doDrop > (.85))
	{
	    dropPotion(killer, paul);
	}
	return doDrop;
    }

    public void dropPotion(Player killer, Entity paul)
    {
	ItemStack potion = new ItemStack(Material.POTION);
	ItemMeta potmeta = potion.getItemMeta();
	if (level < 5)
	{
	    potmeta.setDisplayName("Minor Healing Potion");
	    lore.add("Heals Minor Damage");
	    lore.add(ChatColor.BLUE + "Sell Value: 3");
	    lore.add("PlayerI" + killer.getUniqueId());
	}
	if (level > 5)
	{
	    potmeta.setDisplayName("Light Healing Potion");
	    lore.add("Heals Light Damage");
	    lore.add(ChatColor.BLUE + "Sell Value: 7");
	    lore.add("PlayerI" + killer.getUniqueId());
	}
	potmeta.setLore(lore);
	potion.setItemMeta(potmeta);
	lore.clear();
    }
    public void dropCommon(int level, Player killer, Entity paul)
    {
	hdmg = t.nextInt(level + 3) + 1;
	if (hdmg > 5)
	    hdmg = 5;
	lowdmg = t.nextInt(level) + 1;
	if (lowdmg > 4)
	    lowdmg = 4;
	armwep = t.nextInt(2);
	double subtype = Math.random();
	type = Math.random();
	if (lowdmg > hdmg)
	{
	    lowdmg -= (lowdmg - hdmg);
	}
	else if (lowdmg == hdmg)
	{
	    hdmg++;
	}
	if (armwep == 0)
	{
	    if (level <= 10)
	    {
		if (type <= .25)
		{
		    drop.setType(Material.WOOD_SWORD);
		    suffix = "Wooden Sword";
		}
		else if (type > .25 && type <= .50)
		{
		    drop.setType(Material.WOOD_AXE);
		    suffix = "Wooden Axe";
		}
		else if (type > .50 && type <= .75)
		{
		    drop.setType(Material.SHEARS);
		    suffix = "Claws";
		}
		else if (type > .75 && type <= 1)
		{
		    drop.setType(Material.IRON_HOE);
		    suffix = "Scythe";
		}
		else if (hdmg <= level)
		{
		    prefix = ChatColor.GRAY + "Drunk-Made ";
		}
		else if (hdmg >= level + 1)
		{
		    prefix = ChatColor.GRAY + "Crappy ";
		}
		else if (hdmg >= level + 2)
		{
		    prefix = ChatColor.GRAY + "Low-Quality ";
		}
	    }
	    else if (level > 10 && level <= 20)
	    {
		if (type <= .25)
		{
		    drop.setType(Material.STONE_AXE);
		    suffix = "Stone Axe";
		}
		else if (type > .25 && type <= .50)
		{
		    drop.setType(Material.STONE_SWORD);
		    suffix = "Stone Sword";
		}
		else if (type > .50 && type <= .75)
		{
		    drop.setType(Material.SHEARS);
		    suffix = "Assassin Claws";
		}
		else if (type > .75 && type <= 1)
		{
		    drop.setType(Material.IRON_HOE);
		    suffix = "Sickle";
		}
		if (hdmg <= level)
		{
		    if (subtype >= 50)
		    {
			prefix = ChatColor.GRAY + "Poorly Made ";
		    }
		    else if (subtype < 50)
		    {
			prefix = ChatColor.GRAY + "Broken ";
			hdmg = 0;
		    }
		}
		else if (hdmg >= level + 1)
		{
		    if (subtype >= 50)
		    {
			prefix = ChatColor.GRAY + "Badly Made ";
		    }
		    else if (subtype < 50)
		    {
			prefix = ChatColor.GRAY + "Shoddy ";
			hdmg -= 2;
		    }
		}
		else if (hdmg >= level + 2)
		{
		    if (subtype >= 50)
		    {
			prefix = ChatColor.GRAY + "Mediocre ";
		    }
		    else if (subtype < 50)
		    {
			prefix = ChatColor.GRAY + "Dull ";
			hdmg -= 1;
		    }
		}
		hdmg += 1;
		lowdmg += 1;
	    }
	    if (level > 20)
	    {
		if (type <= .25)
		{
		    drop.setType(Material.STONE_AXE);
		    suffix = "Maul";
		}
		else if (type > .25 && type <= .50)
		{
		    drop.setType(Material.IRON_SWORD);
		    suffix = "Iron Sword";
		}
		else if (type > .50 && type <= .75)
		{
		    drop.setType(Material.SHEARS);
		    suffix = "Katar";
		}
		else if (type > .75 && type <= 1)
		{
		    drop.setType(Material.IRON_HOE);
		    suffix = "Sickle";
		}
		if (hdmg <= level + 1)
		{
		    if (subtype >= 50)
		    {
			prefix = ChatColor.GRAY + "Splintered ";
		    }
		    else if (subtype < 50)
		    {
			prefix = ChatColor.GRAY + "Broken ";
			hdmg = 0;
		    }
		}
		else if (hdmg >= level + 2)
		{
		    if (subtype >= 50)
		    {
			prefix = ChatColor.GRAY + "Edentate ";
		    }
		    else if (subtype < 50)
		    {
			prefix = ChatColor.GRAY + "Blunt ";
			hdmg = 0;
		    }
		}
		else if (hdmg >= level + 3)
		{
		    if (subtype >= 50)
		    {
			prefix = ChatColor.GRAY + "Jagged ";
		    }
		    else if (subtype < 50)
		    {
			prefix = ChatColor.GRAY + "Barbed ";
			hdmg = 0;
		    }
		}
		hdmg += 4;
		lowdmg += 2;
	    }
	    value = value + ((hdmg + lowdmg) - 2);
	}
	if (armwep == 1)
	{
	    armor = true;
	    if (level <= 10)
	    {
		defense = t.nextInt(level + 2);
		if (type <= .25)
		{
		    drop.setType(Material.LEATHER_BOOTS);
		    suffix = ChatColor.GRAY + "Leather Boots";
		}
		else if (type <= .50 && type > .25)
		{
		    drop.setType(Material.LEATHER_CHESTPLATE);
		    suffix = ChatColor.GRAY + "Leather Body Armor";
		}
		else if (type > .50 && type <= .75)
		{
		    drop.setType(Material.LEATHER_HELMET);
		    suffix = ChatColor.GRAY + "Leather Coif";
		}
		else if (type > .75)
		{
		    drop.setType(Material.LEATHER_LEGGINGS);
		    suffix = ChatColor.GRAY + "Leather Chaps";
		}
		if (defense <= level)
		{
		    prefix = ChatColor.GRAY + "Drunk-Made ";
		}
		else if (defense >= level + 1)
		{
		    prefix = ChatColor.GRAY + "Crummy ";
		}
		else if (defense >= level + 2)
		{
		    prefix = ChatColor.GRAY + "Low-Quality ";
		}
	    }
	    else if (level > 10 && level <= 20)
	    {
		defense = t.nextInt(level + 3);
		if (type <= .25)
		{
		    drop.setType(Material.LEATHER_BOOTS);
		    suffix = ChatColor.GRAY + "Leather Boots";
		}
		else if (type <= .50 && type > .25)
		{
		    drop.setType(Material.LEATHER_CHESTPLATE);
		    suffix = ChatColor.GRAY + "Leather Body Armor";
		}
		else if (type > .50 && type <= .75)
		{
		    drop.setType(Material.LEATHER_HELMET);
		    suffix = ChatColor.GRAY + "Leather Coif";
		}
		else if (type > .75)
		{
		    drop.setType(Material.LEATHER_LEGGINGS);
		    suffix = ChatColor.GRAY + "Leather Chaps";
		}
		if (defense <= level)
		{
		    prefix = ChatColor.GRAY + "Cracked ";
		}
		else if (defense >= level + 1)
		{
		    prefix = ChatColor.GRAY + "Threadbare ";
		}
		else if (defense >= level + 2)
		{
		    prefix = ChatColor.GRAY + "Torn ";
		}
		else if (defense >= level + 3)
		{
		    prefix = ChatColor.GRAY + "Moth-eaten ";
		}
	    }
	    else if (level > 20)
	    {
		if (type <= .25)
		{
		    drop.setType(Material.CHAINMAIL_LEGGINGS);
		    suffix = ChatColor.GRAY + "Iron Woven Leggings";
		}
		else if (type <= .50 && type > .25)
		{
		    drop.setType(Material.CHAINMAIL_CHESTPLATE);
		    suffix = ChatColor.GRAY + "Chain Weave Plackart";
		}
		else if (type > .50 && type <= .75)
		{
		    drop.setType(Material.CHAINMAIL_HELMET);
		    suffix = ChatColor.GRAY + "Reinforced Helmet";
		}
		else if (type > .75)
		{
		    drop.setType(Material.CHAINMAIL_BOOTS);
		    suffix = ChatColor.GRAY + "Chain Boots";
		}
		if (defense <= level)
		{
		    prefix = ChatColor.GRAY + "Sun Baked ";
		}
		else if (defense >= level + 1)
		{
		    prefix = ChatColor.GRAY + "Rusty ";
		}
		else if (defense >= level + 2)
		{
		    prefix = ChatColor.GRAY + "Degraded ";
		}
		else if (defense >= level + 3)
		{
		    prefix = ChatColor.GRAY + "Stiff ";
		}
	    }
	    if (defense > 1)
	    {
		value = value + (defense - 1);
	    }
	    else
	    {
		value = value + 1;
	    }
	}
	drop(killer, paul);
    }

    public void dropUncommon(int level, Player killer, Entity paul)
    {
	hdmg = t.nextInt(level + 2) + 2;
	lowdmg = t.nextInt(level) + 1;
	armwep = t.nextInt(2);
	type = Math.random();
	if (lowdmg > hdmg)
	{
	    lowdmg -= (lowdmg - hdmg);
	}
	if (armwep == 0)
	{

	    if (level <= 10)
	    {
		if (type < .50)
		{
		    drop.setType(Material.WOOD_SWORD);
		    suffix = "Wooden Sword";
		}
		else if (type >= .50 && type <= .65)
		{
		    drop.setType(Material.IRON_SWORD);
		    suffix = "Iron Sword";
		}
		else if (type > .65)
		{
		    drop.setType(Material.WOOD_AXE);
		    suffix = "Wooden Axe";
		}
		if (level >= 4 && Math.random() > .50)
		{
		    hdmg += 1;
		}
		else if (hdmg <= level + 1)
		{
		    prefix = "Decent ";
		}
		else if (hdmg >= level + 2)
		{
		    prefix = "Average ";
		}
		else if (hdmg >= level + 3)
		{
		    prefix = "Above-Average ";
		}
		else if (hdmg >= level + 4)
		{
		    prefix = "Dull ";
		}

	    }
	    else if (level > 10)
	    {
		if (type < .50)
		{
		    drop.setType(Material.WOOD_SWORD);
		    suffix = "Wooden Sword";
		}
		else if (type >= .50 && type <= .65)
		{
		    drop.setType(Material.STONE_SWORD);
		    suffix = "Stone Sword";
		}
		else if (type > .65)
		{
		    drop.setType(Material.WOOD_AXE);
		    suffix = "Wooden Axe";
		}
		if (level >= 4 && Math.random() > .50)
		{
		    hdmg += 1;
		}
		if (hdmg <= level + 1)
		{
		    prefix = "Mediocre ";
		}
		else if (hdmg >= level + 2)
		{
		    prefix = "Suitable ";
		}
		else if (hdmg >= level + 3)
		{
		    prefix = "Unblemished ";
		}
		else if (hdmg >= level + 4)
		{
		    prefix = "Sharp ";
		}
	    }
	    if (level > 20)
	    {

	    }
	    value = value + ((hdmg + lowdmg) - 2);
	}
	else if (armwep == 1)
	{
	    armor = true;
	    if (level <= 10)
	    {
		defense = t.nextInt(level + 2);
		if (type <= .25)
		{
		    drop.setType(Material.IRON_BOOTS);
		    suffix = "Basic Greaves";
		}
		if (type <= .50 && type > .25)
		{
		    drop.setType(Material.LEATHER_CHESTPLATE);
		    suffix = "Leather Body Armor";
		}
		if (type > .50 && type <= .75)
		{
		    drop.setType(Material.IRON_HELMET);
		    suffix = "Helmet";
		}
		if (type > .75)
		{
		    drop.setType(Material.LEATHER_LEGGINGS);
		    suffix = "Leather Chaps";
		}
		if (defense <= level + 1)
		{
		    prefix = "Mediocre ";
		}
		if (defense >= level + 2)
		{
		    prefix = "Average ";
		}
		if (defense >= level + 3)
		{
		    prefix = "Above-Average ";
		}
		defense += 1;
		if (defense > 1)
		{
		    value = value + (defense - 1);
		}
		else
		{
		    value = value + 1;
		}
	    }
	    if (level > 10 && level <= 25)
	    {
		defense = t.nextInt(level + 4);
		if (type <= .25)
		{
		    drop.setType(Material.IRON_BOOTS);
		    suffix = "Basic Greaves";
		}
		if (type <= .50 && type > .25)
		{
		    drop.setType(Material.LEATHER_CHESTPLATE);
		    suffix = "Leather Body Armor";
		}
		if (type > .50 && type <= .75)
		{
		    drop.setType(Material.IRON_HELMET);
		    suffix = "Basic Helmet";
		}
		if (type > .75)
		{
		    drop.setType(Material.LEATHER_LEGGINGS);
		    suffix = "Leather Chaps";
		}
		if (defense <= level + 1)
		{
		    prefix = "Beaten ";
		}
		if (defense >= level + 2)
		{
		    prefix = "Standard ";
		}
		if (defense >= level + 3)
		{
		    prefix = "Solid ";
		}
		if (defense >= level + 3)
		{
		    prefix = "Reinforced ";
		}
		defense += 1;
		if (defense > 1)
		{
		    value = value + (defense - 1);
		}
		else
		{
		    value = value + 1;
		}
	    }
	}
	drop(killer, paul);

    }

    public void dropRare(int level, Player killer, Entity paul)
    {
	double decider = Math.random();
	armwep = t.nextInt(2);
	if (armwep == 0)
	{
	    if (level <= 10)
	    {
		if (decider <= .10)
		{
		    prefix = (ChatColor.GREEN + "The Knicker");
		    suffix = "";
		    drop.setType(Material.IRON_AXE);
		    hdmg = 6;
		    lowdmg = 4;
		}
		if (decider <= .40 && decider > .10)
		{
		    prefix = (ChatColor.GREEN + "GutRipper");
		    suffix = "";
		    drop.setType(Material.STONE_SWORD);
		    hdmg = 10;
		    lowdmg = 2;
		}
		if (decider <= 1 && decider > .40)
		{
		    prefix = (ChatColor.GREEN + "Armot's Spear");
		    suffix = "";
		    drop.setType(Material.IRON_SPADE);
		    hdmg = 10;
		    lowdmg = 4;
		}
		// lowlvl-rare loot table
	    }
	    if (level > 10 && level <= 25)
	    {
		if (decider <= .10)
		{
		    prefix = (ChatColor.GREEN + "Night's Edge");
		    suffix = "";
		    drop.setType(Material.GOLD_AXE);
		    hdmg = 19;
		    lowdmg = 9;
		}
		if (decider <= .40 && decider > .10)
		{
		    prefix = (ChatColor.GREEN + "Nightbane");
		    suffix = "";
		    drop.setType(Material.IRON_SWORD);
		    hdmg = 18;
		    lowdmg = 9;
		}
		if (decider <= 1 && decider > .40)
		{
		    prefix = (ChatColor.GREEN + "Brainhacker");
		    suffix = "";
		    drop.setType(Material.GOLD_AXE);
		    hdmg = 16;
		    lowdmg = 9;
		}
	    }
	    if (level > 20 && level < 30)
	    {

	    }
	    value = value + ((hdmg + lowdmg) + 15);
	}
	if (armwep == 1)
	{
	    armor = true;
	    if (level <= 10)
	    {
		if (decider <= .10)
		{
		    prefix = (ChatColor.GREEN + "Helm of Honor");
		    suffix = "";
		    drop.setType(Material.IRON_HELMET);
		    defense = 11;
		}
		if (decider <= .20 && decider > .10)
		{
		    prefix = (ChatColor.GREEN + "Warboots");
		    suffix = "";
		    drop.setType(Material.IRON_BOOTS);
		    defense = 12;
		}
		if (decider <= .40 && decider > .20)
		{
		    prefix = (ChatColor.GREEN + "Cuirass of the Wind");
		    suffix = "";
		    drop.setType(Material.IRON_CHESTPLATE);
		    defense = 15;
		}
		if (decider <= 1 && decider > .40)
		{
		    prefix = (ChatColor.GREEN + "Jade Infused Legplates");
		    suffix = "";
		    drop.setType(Material.IRON_LEGGINGS);
		    defense = 11;
		}
		// lowlvl-rare loot table
	    }
	    if (level > 10 && level <= 25)
	    {
		if (decider <= .10)
		{
		    prefix = (ChatColor.GREEN + "Helm of Valor");
		    suffix = "";
		    drop.setType(Material.GOLD_HELMET);
		    defense = 14;
		}
		if (decider <= .20 && decider > .10)
		{
		    prefix = (ChatColor.GREEN + "Emporer's Sabatons");
		    suffix = "";
		    drop.setType(Material.GOLD_HELMET);
		    defense = 15;
		}
		if (decider <= 1 && decider > .20)
		{
		    prefix = (ChatColor.GREEN + "King's Breastplate");
		    suffix = "";
		    drop.setType(Material.GOLD_CHESTPLATE);
		    defense = 17;
		}
	    }
	    if (level > 20 && level < 30)
	    {
		if (decider <= .10)
		{
		    prefix = (ChatColor.GREEN + "Mercurial Helm");
		    suffix = "";
		    drop.setType(Material.GOLD_HELMET);
		    defense = 14;
		}
		if (decider <= .20 && decider > .10)
		{
		    prefix = (ChatColor.GREEN + "Gladiators Treads");
		    suffix = "";
		    drop.setType(Material.GOLD_BOOTS);
		    defense = 15;
		}
		if (decider <= .40 && decider > .20)
		{
		    prefix = (ChatColor.GREEN + "Helm of Bartuc");
		    suffix = "";
		    drop.setType(Material.LEATHER_HELMET);
		    defense = 17;
		}
		if (decider <= 1 && decider > .40)
		{
		    prefix = (ChatColor.GREEN + "Juggernaught Plates");
		    suffix = "";
		    drop.setType(Material.IRON_CHESTPLATE);
		    defense = 17;
		}
	    }
	    value = value + (defense + 15);
	}
	drop(killer, paul);
    }

    public void dropEpic(int level, EntityDeathEvent event)
    {
	if (level < 10)
	{
	    prefix = (ChatColor.DARK_PURPLE + "The Hallowed Scythe");
	    suffix = "";
	    drop.setType(Material.IRON_HOE);
	    hdmg = 26;
	    lowdmg = 18;
	}
	if (level > 10 && level < 20)
	{
	    prefix = (ChatColor.DARK_PURPLE + "Omen Claws");
	    suffix = "";
	    drop.setType(Material.SHEARS);
	    hdmg = 20;
	    lowdmg = 13;
	}
	if (level > 20 && level < 30)
	{
	    prefix = (ChatColor.DARK_PURPLE + "The Hallowed Scythe");
	    suffix = "";
	    drop.setType(Material.IRON_HOE);
	    hdmg = 26;
	    lowdmg = 18;
	}
	value = value + ((hdmg + lowdmg) + 40);

    }

    public void dropLegendary(int level, EntityDeathEvent event)
    {
	// Legendary Loot table.
    }

    public void drop(Player killer, Entity paul)
    {
	if (armor == false)
	{
	    lore.add("Damage: " + lowdmg + "-" + hdmg);
	}
	if (armor == true)
	{
	    lore.add("Defense: " + defense);
	}
	lore.add(ChatColor.BLUE + value);
	dropmeta.setDisplayName(prefix + suffix + " ");
	drop.setDurability((short) 0);
	armor = false;
	dropmeta.setLore(lore);
	drop.setItemMeta(dropmeta);
	if (dropmeta.getDisplayName().equals("Helm of Bartuc"))
	{
	    LeatherArmorMeta lm = (LeatherArmorMeta) drop.getItemMeta();
	    lm.setColor(Color.RED);
	    drop.setItemMeta(lm);
	}
	new BukkitRunnable()
	{
	    public void run()
	    {
		Item i = paul.getWorld().dropItem(paul.getLocation(), drop);
		MetaDataUtil.setMetaString(i,"killer", killer.getUniqueId().toString());
	    }
	}.runTask(BlackLance.getPlugin());
	Bukkit.broadcastMessage("attempting drop..");
	lore.clear();
    }
}
