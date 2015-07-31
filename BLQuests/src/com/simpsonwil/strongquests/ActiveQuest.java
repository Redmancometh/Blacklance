package com.simpsonwil.strongquests;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;

/**
 * Created by Wil on 1/22/2015.
 */
public class ActiveQuest implements Listener
{
    //The quest that is active
    private Quest quest;
    //The player doing the active quest
    private Player player;
    /**
     * Create a new active quest for a given quest and player
     *
     * @param activeQuest quest to be active for. Be sure to have it be a clone.
     * @param questTaker player active for the particular quest
     */
    public ActiveQuest(Quest activeQuest, Player questTaker)
    {
        this.quest = activeQuest;
        this.player = questTaker;
    }

    @EventHandler
    public void onQuestPartComplete(QuestPartFinishedEvent e)
    {
        Player finisher = e.getPlayer();
        QuestPart finishedPart = e.getQuestPart();
        QuestPart currentPart = getCurrentPart();

        if(finisher.equals(player) && currentPart.equals(finishedPart))
        {
            continueQuest();
        }
    }

    /**
     * Get the player taking the quest
     *
     * @return player taking the quest
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Get the quest that is active
     *
     * @return active quest
     */
    public Quest getQuest()
    {
        return quest;
    }

    /**
     * Since the first index in the ArrayList is the current part of the quest return the first index of the quest
     *
     * @return the current quest part
     */
    public QuestPart getCurrentPart()
    {
        ArrayList<QuestPart> questParts = quest.getQuestParts();
        if(questParts.size() > 0)
        {
            return questParts.get(0);
        }

        return null;
    }

    /**
     * Since the first index in the ArrayList is the current part of the quest, check if there are more than one part
     * left in the quest. If there is only one part left, the quest has been completed. Otherwise, remove the first
     * index so now the new index will be the next part.
     */
    private void continueQuest()
    {
        ArrayList<QuestPart> questParts = quest.getQuestParts();
        if(questParts.size() > 1)
        {
            questParts.remove(0);
        }
        else
        {
            questFinished();
        }
    }

    private void questFinished()
    {
        Bukkit.getServer().getPluginManager().callEvent(new QuestFinishedEvent(quest, player));
    }
}
