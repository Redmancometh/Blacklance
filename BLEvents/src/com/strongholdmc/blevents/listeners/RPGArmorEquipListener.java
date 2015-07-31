package com.strongholdmc.blevents.listeners;

import com.strongholdmc.blcore.main.RPGStat;
import com.strongholdmc.blcore.playerclasses.RPGArmor;
import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import com.strongholdmc.blevents.equipevents.ChangeRPGArmorEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Created by wil on 6/19/2015.
 */
public class RPGArmorEquipListener implements Listener
{
    public RPGArmorEquipListener()
    {

    }

    @EventHandler
    public void onRPGArmorChange(ChangeRPGArmorEvent e)
    {
        RPGPlayer rpgPlayer = e.getRPGPlayer();
        RPGArmor rpgArmor = e.getRPGArmor();

        boolean add = e.wasEquipped();

        for(RPGStat stat : RPGStat.values())
        {
            if(add)
            {
                rpgPlayer.incrementModStat(stat, rpgArmor.getStatValue(stat));
            }
            else
            {
                rpgPlayer.decrementModStat(stat, rpgArmor.getStatValue(stat));
            }
        }
    }
}
