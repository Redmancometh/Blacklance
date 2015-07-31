package com.strongholdmc.blmerchants.trait;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;

import com.strongholdmc.blmerchants.main.Main;
public class CitizenUtil
{
    public static void spawnAllMerchants()
    {
	for(long id : Main.getCache().getAllID())
	{
	    System.out.println(" t"+id);
	    Location loc = Main.getCache().getLocation(id);
	    NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.VILLAGER, Main.getCfg().getTitle(id));
	    npc.addTrait(BLMerchant.class);
	    npc.getTrait(BLMerchant.class).setID(id);
	    npc.spawn(loc);
	}
    }
}
