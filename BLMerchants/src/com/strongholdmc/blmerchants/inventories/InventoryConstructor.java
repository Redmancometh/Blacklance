package com.strongholdmc.blmerchants.inventories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.strongholdmc.blmerchants.cfg.CfgUtil;

public class InventoryConstructor
{
    public static Inventory makeInventory(String title, List<String> names, List<Long> values, List<Material> materials, List<String[]> lores)
    {
	Inventory inv = Bukkit.createInventory(null, 36, title);
	for(int x = 0; x<names.size(); x++)
	{
	    inv.addItem(createItemStack(names.get(x), values.get(x), materials.get(x), lores.get(x)));
	}
	return inv;
    }
    public static ItemStack createItemStack(String name, long value, Material m, String...lore)
    {
	ItemStack toReturn = new ItemStack(m);
	ItemMeta iMeta = toReturn.getItemMeta();
	List<String> loreList = new ArrayList<String>(Arrays.asList(lore));
	loreList.add(ChatColor.BLUE+"Buy for: "+value);
	loreList.add(CfgUtil.convertToInvisibleString(value+""));
	iMeta.setDisplayName(name);
	iMeta.setLore(loreList);
	toReturn.setItemMeta(iMeta);
	return toReturn;
    }
}
