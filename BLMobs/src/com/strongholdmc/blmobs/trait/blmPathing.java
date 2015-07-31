package com.strongholdmc.blmobs.trait;

import java.util.Random;

import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Location;

public class blmPathing 
{
    Random r1 = new Random();
    public Location findGoal(NPC npc)
    {
	Location loc1 = npc.getEntity().getLocation();
	int xy = r1.nextInt(2)+1;
	int addition = r1.nextInt(8)+1;
	if(xy==1)
	{
	    return loc1.add(addition,0,0);
	}
	else if(xy==2)
	{
	    return loc1.add(0,0,addition);
	}
	else
	{
	    System.out.println("BAD THINGS HAPPENED DURING PATHFINDING!");
	    return null;
	}
    }
}
