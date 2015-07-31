package com.strongholdmc.blmerchants.caching;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

public class CachedVars
{
    private List<Long> merchantIDs = new ArrayList<Long>();
    private HashMap<Long, Inventory> merchInvs = new HashMap<Long, Inventory>();
    private HashMap<Long, Location> merchLocs = new HashMap<Long, Location>();
    public void addAll(List<Long> idList)
    {
	this.merchantIDs=idList;
    }
    public void addMerchant(long id)
    {
	merchantIDs.add(id);
    }
    public List<Long> getAllID()
    {
	return merchantIDs;
    }
    public void addMerchantInventory(long id, Inventory inv)
    {
	merchInvs.put(id, inv);
    }
    public Location getLocation(long id)
    {
	return merchLocs.get(id);
    }
    public void addMerchantLocation(long id, Location loc)
    {
	merchLocs.put(id, loc);
    }
    public boolean isMerchant(long id)
    {
	for(long x : merchantIDs)
	{
	    if(id==x)
	    {
		return true;
	    }
	}
	return false;
    }
    public Inventory getInventory(long id)
    {
	return merchInvs.get(id);
    }
}
