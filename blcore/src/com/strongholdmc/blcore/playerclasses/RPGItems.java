package com.strongholdmc.blcore.playerclasses;

import java.util.HashMap;

public class RPGItems
{
    static HashMap<String, RPGWeapon> weaponMap = new HashMap<String, RPGWeapon>();
    static HashMap<String, RPGArmor> armorMap = new HashMap<String, RPGArmor>();
    
    public static void addToMap(String serialized, RPGWeapon w)
    {
	weaponMap.put(serialized, w);
    }

    public static void addToMap(String serialized, RPGArmor a)
    {
	armorMap.put(serialized, a);
    }
    
    public static RPGArmor getArmor(String s)
    {
	return armorMap.get(s);
    }
    
    public static RPGWeapon getWeapon(String s)
    {
	return weaponMap.get(s);
    }

    public static boolean hasArmor(String s)
    {
        return armorMap.containsKey(s);
    }

    public static boolean hasWeapon(String s)
    {
        return weaponMap.containsKey(s);
    }
}
