package com.simpsonwil.strongquests;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Wil on 1/22/2015.
 */
public class QuestFinishedEvent extends Event
{
    //Handlers for the event
    private static final HandlerList handlers = new HandlerList();

    //Is the event being cancelled
    private boolean cancelled = false;

    //Quest being completed
    private Quest quest;

    //Player that finished the quest
    private Player player;

    public QuestFinishedEvent(Quest finishedQuest, Player finisher)
    {
        this.quest = finishedQuest;
        this.player = finisher;
    }

    /**
     * Check if the event is cancelled
     *
     * @return true if event was cancelled
     */
    public boolean isCancelled()
    {
        return cancelled;
    }

    /**
     * Make the event cancelled
     *
     * @param cancel true to set the event to be cancelled
     */
    public void setCancelled(boolean cancel)
    {
        cancelled = cancel;
    }

    /**
     * Get the quest finished
     *
     * @return quest that was finished
     */
    public Quest getQuest()
    {
        return quest;
    }

    /**
     * Get the player that finished the quest
     *
     * @return player that finished the quest
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Return the handlers for this event instance
     *
     * @return handlers for this event instance
     */
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Get the all the handlers for this event
     *
     * @return handlers for this event
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }
}
