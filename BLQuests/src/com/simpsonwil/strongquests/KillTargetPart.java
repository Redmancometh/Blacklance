package com.simpsonwil.strongquests;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Created by Wil on 1/22/2015.
 */
public class KillTargetPart extends QuestPart implements Listener
{
    //Target player needs to kill for this quest part
    private LivingEntity target;

    /**
     *
     * @param target target player needs to kill for this quest part
     */
    public KillTargetPart(LivingEntity target)
    {
        this.target = target;
    }

    /**
     * Get the target for the part of this quest
     *
     * @return target for quest
     */
    public LivingEntity getTarget()
    {
        return target;
    }

    @EventHandler
    public void onTargetKill(EntityDeathEvent e)
    {
        LivingEntity entity = e.getEntity();

        //Check if it's the correct entity died
        if(entity.getUniqueId().equals(target.getUniqueId()))
        {
            LivingEntity killer = entity.getKiller();

            //Check if the killer is a player
            if(killer instanceof Player)
            {
                Bukkit.getServer().getPluginManager().callEvent(new QuestPartFinishedEvent(this, entity.getKiller()));
            }
        }
    }
}
