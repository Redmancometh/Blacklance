package com.strongholdmc.blevents.listeners;
import com.strongholdmc.blcore.storage.RPGPlayers;
import com.strongholdmc.blevents.equipevents.ChangeRPGArmorEvent;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

//@TODO PlayerInteractAtEntityEvent (armor stand)
public class ArmorEquipListener implements Listener
{
    public static final int HELMET_SLOT = 103;
    public static final int CHESTPLATE_SLOT = 102;
    public static final int LEGGINGS_SLOT = 101;
    public static final int BOOTS_SLOT = 100;

    private PluginManager pm;

    public ArmorEquipListener(Plugin p)
    {
        pm = p.getServer().getPluginManager();
    }

    //@TODO Finish
    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e)
    {
        Inventory inv = e.getInventory();
        if(inv instanceof PlayerInventory)
        {
            PlayerInventory pInv = (PlayerInventory) inv;
            int clickedSlot = e.getSlot();
            ItemStack clickedItem = pInv.getItem(clickedSlot);
            Player p = Bukkit.getPlayer(e.getWhoClicked().getName());

            //Handle all left click posibilities
            if(e.isLeftClick())
            {
                if(clickedSlot == HELMET_SLOT
                        || clickedSlot == CHESTPLATE_SLOT
                        || clickedSlot == LEGGINGS_SLOT
                        || clickedSlot == BOOTS_SLOT)
                {

                    ItemStack cursorItem = e.getCursor();

                    //Check if they are replacing an armor slot with an armor in the cursor
                    if(cursorItem != null)
                    {
                        //Make sure it was clicked to go in t he correct spot. (ie- helmet placed in helmet slot)
                        if(isArmorEquippableAt(cursorItem, clickedSlot))
                        {
                            callEquipEvent(cursorItem, p);
                        }
                    }
                    else
                    {
                        if(clickedItem != null)
                        {
                            callUnequipEvent(clickedItem, p);
                        }
                    }
                }
            }
            if(e.isRightClick())
            {
                //Check if armor was removed with right click
                if(clickedSlot == HELMET_SLOT
                        || clickedSlot == CHESTPLATE_SLOT
                        || clickedSlot == LEGGINGS_SLOT
                        || clickedSlot == BOOTS_SLOT)
                {
                    callUnequipEvent(clickedItem, p);
                }
                //Check if armor was added with right click
                else if(isEquipable(clickedItem))
                {
                    callEquipEvent(clickedItem, p);
                }
            }
        }
    }

    @EventHandler
    public void onRightClickEquip(PlayerInteractEvent e)
    {
        //Check if right clicked with armor in hand.
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
        {
            Player p = e.getPlayer();
            if(isEquipable(p.getItemInHand()))
            {
                callEquipEvent(p.getItemInHand(), p);
            }
        }
    }

    @EventHandler
    public void onItemFrameEquip(PlayerInteractEntityEvent e)
    {
        Entity entity = e.getRightClicked();
        if(entity instanceof ItemFrame)
        {
            ItemFrame itemFrame = (ItemFrame) entity;
            if(isEquipable(itemFrame.getItem()))
            {
                callEquipEvent(itemFrame.getItem(), e.getPlayer());
            }
        }
    }

    //@NOTE: Not sure how to handle this?
    @EventHandler
    public void onArmorStandInteract(PlayerInteractAtEntityEvent e)
    {
        Entity entity = e.getRightClicked();
        if(entity instanceof ArmorStand)
        {
            ArmorStand armorStand = (ArmorStand) entity;
        }
    }

    public void callUnequipEvent(ItemStack is, Player p)
    {
        pm.callEvent(new ChangeRPGArmorEvent(is, RPGPlayers.getRPGPlayer(p), false));
    }

    public void callEquipEvent(ItemStack is, Player p)
    {
        pm.callEvent(new ChangeRPGArmorEvent(is, RPGPlayers.getRPGPlayer(p), true));

        //Check for replacement armor
        PlayerInventory inv = p.getInventory();

        if(isHelmetItemStack(is))
        {
            if(inv.getHelmet() != null)
            {
                pm.callEvent(new ChangeRPGArmorEvent(inv.getHelmet(), RPGPlayers.getRPGPlayer(p), false));
            }
        }
        else if(isChestplateItemStack(is))
        {
            if (inv.getChestplate() != null)
            {
                pm.callEvent(new ChangeRPGArmorEvent(inv.getChestplate(), RPGPlayers.getRPGPlayer(p), false));
            }
        }
        else if(isLeggingsItemStack(is))
        {
            if(inv.getLeggings() != null)
            {
                pm.callEvent(new ChangeRPGArmorEvent(inv.getLeggings(), RPGPlayers.getRPGPlayer(p), false));
            }
        }
        else if(isBootsItemStack(is))
        {
            if(inv.getBoots() != null)
            {
                pm.callEvent(new ChangeRPGArmorEvent(inv.getBoots(), RPGPlayers.getRPGPlayer(p), false));
            }
        }
    }

    public boolean isArmorEquippableAt(ItemStack is, int slot)
    {
        if(slot == HELMET_SLOT     && isHelmetItemStack(is))     return true;
        if(slot == CHESTPLATE_SLOT && isChestplateItemStack(is)) return true;
        if(slot == LEGGINGS_SLOT   && isLeggingsItemStack(is))   return true;
        if(slot == BOOTS_SLOT      && isBootsItemStack(is))      return true;

        return false;
    }

    public boolean isHelmetItemStack(ItemStack is)
    {
        switch(is.getType())
        {
            case CHAINMAIL_HELMET:
            case DIAMOND_HELMET:
            case GOLD_HELMET:
            case IRON_HELMET:
            case LEATHER_HELMET:
                return true;

            default:
                return false;
        }
    }

    public boolean isChestplateItemStack(ItemStack is)
    {
        switch(is.getType())
        {
            case CHAINMAIL_CHESTPLATE:
            case DIAMOND_CHESTPLATE:
            case GOLD_CHESTPLATE:
            case IRON_CHESTPLATE:
            case LEATHER_CHESTPLATE:
                return true;

            default:
                return false;
        }
    }

    public boolean isLeggingsItemStack(ItemStack is)
    {
        switch(is.getType())
        {
            case CHAINMAIL_LEGGINGS:
            case DIAMOND_LEGGINGS:
            case GOLD_LEGGINGS:
            case IRON_LEGGINGS:
            case LEATHER_LEGGINGS:
                return true;

            default:
                return false;
        }
    }

    public boolean isBootsItemStack(ItemStack is)
    {
        switch(is.getType())
        {
            case CHAINMAIL_BOOTS:
            case DIAMOND_BOOTS:
            case GOLD_BOOTS:
            case IRON_BOOTS:
            case LEATHER_BOOTS:
                return true;

            default:
                return false;
        }
    }

    private boolean isEquipable(ItemStack i)
    {
        ItemStack test = new ItemStack(i.getType());

        try
        {
            test.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1);
        }
        catch(IllegalArgumentException e)
        {
            return false;
        }

        return true;
    }
}
