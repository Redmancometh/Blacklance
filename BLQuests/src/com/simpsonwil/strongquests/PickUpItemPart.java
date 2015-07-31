package com.simpsonwil.strongquests;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Wil on 1/22/2015.
 */
public class PickUpItemPart extends QuestPart implements Listener
{
    //Item that needs to be picked up
    private ItemStack item;

    /**
     *
     * @param itemToPickup item needing to be picked up
     */
    public PickUpItemPart(ItemStack itemToPickup)
    {
        this.item = itemToPickup;
    }

    /**
     * Get the item that needs to be picked up
     *
     * @return item item that needs to be picked up
     */
    public ItemStack getItem()
    {
        return item;
    }



    @EventHandler
    public void onPickup(PlayerPickupItemEvent e)
    {
        ItemStack pickedUpItem = e.getItem().getItemStack();

        //Check if the item being picked up is the quest part item
        if(item.equals(pickedUpItem))
        {
            Bukkit.getServer().getPluginManager().callEvent(new QuestPartFinishedEvent(this, e.getPlayer()));
        }
    }
}
