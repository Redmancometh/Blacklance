package com.simpsonwil.strongquests;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Wil on 1/22/2015.
 */
public class QuestPartFinishedEvent extends Event implements Cancellable
{
    //Handlers for the event
    private static final HandlerList handlers = new HandlerList();

    //Is the event being cancelled
    private boolean cancelled = false;

    //QuestPart being completed
    private QuestPart part;

    //Player that finished the event
    private Player player;

    public QuestPartFinishedEvent(QuestPart part, Player finisher)
    {
        this.part = part;
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
     * Get the quest part that was finished
     *
     * @return quest part that was finished
     */
    public QuestPart getQuestPart()
    {
        return part;
    }

    /**
     * Get the player that finished the quest part
     *
     * @return player that finished the quest part
     */
    public Player getPlayer()
    {
        return player;
    }

    /**
     * Get the handlers for this event instance
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
