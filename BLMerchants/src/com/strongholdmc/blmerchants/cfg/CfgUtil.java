package com.strongholdmc.blmerchants.cfg;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

import com.strongholdmc.blmerchants.caching.CachedVars;
import com.strongholdmc.blmerchants.inventories.InventoryConstructor;
public class CfgUtil
{
    private FileConfiguration merchantCfg;
    private CachedVars cacheProcessor;
    public CfgUtil(FileConfiguration merchantCfg, CachedVars cacheProcessor)
    {
	this.cacheProcessor=cacheProcessor;
	this.merchantCfg=merchantCfg;
    }
    public void setAllInventories()
    {
	for(long idLong : getAllID())
	{
	    long id = (int)idLong;
	    cacheProcessor.addMerchant(id);
	    Inventory inv = InventoryConstructor.makeInventory(getTitle(id)+convertToInvisibleString("pooptard"), getNames(id), getValues(id), getMaterials(id), getLores(id));
	    cacheProcessor.addMerchantInventory(id, inv);
	    cacheProcessor.addMerchantLocation(id, getLoc(id));
	}
    }
    public List<Long> getAllID()
    {
	return merchantCfg.getLongList("IDs");
    }
    public Location getLoc(long id)
    {
	String[] locString = merchantCfg.getString("ID."+id+".location").split(",");
	for(int x = 0; x<locString.length; x++)
	{
	    locString[x] = locString[x].replace(",", "");
	    locString[x] = locString[x].replace(" ", "");
	}
	return new Location(Bukkit.getWorld("world"),Integer.parseInt(locString[0]), Integer.parseInt(locString[1]), Integer.parseInt(locString[2]));
    }
    public String getTitle(long id)
    {
	return merchantCfg.getString("ID."+id+".title");
    }
    public List<String> getNames(long id)
    {
	return merchantCfg.getStringList("ID."+id+".names");
    }
    public List<Long> getValues(long id)
    {
	return merchantCfg.getLongList("ID."+id+".values");
    }
    public List<String[]> getLores(long id)
    {
	List<String[]> loreList = new ArrayList<String[]>();
	for(String s : merchantCfg.getStringList("ID."+id+".lores"))
	{
	    String[] lores = s.split(",");
	    for(int x = 0; x<lores.length; x++)
	    {
		lores[x] = lores[x].replace(",", " ");
		lores[x] = lores[x].replace(" ", "");
	    }
	    loreList.add(lores);
	}
	return loreList;
    }
    public List<Material> getMaterials(long id)
    {
	List<Material> materials = new ArrayList<Material>();
	for(String s : merchantCfg.getStringList("ID."+id+".materials"))
	{
	    materials.add(Material.getMaterial(s));
	}
	return materials;
    }
    public static String convertToInvisibleString(String s)
    {
	String hidden = "";
	for (char c : s.toCharArray())
	    hidden += ChatColor.COLOR_CHAR + "" + c;
	return hidden;
    }
}
