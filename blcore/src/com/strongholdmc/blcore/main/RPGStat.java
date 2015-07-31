package com.strongholdmc.blcore.main;

public enum RPGStat
{
    STRENGTH, AGILITY, INTELLECT, VITALITY, HIGH_DAMAGE, LOW_DAMAGE, DEFENSE;
    public static RPGStat getByName(String statName)
    {
	for(RPGStat stat  : RPGStat.values())
	{
	    if(stat.name().equals(statName))
	    {
		return stat;
	    }
	}
	return null;
    }
}
