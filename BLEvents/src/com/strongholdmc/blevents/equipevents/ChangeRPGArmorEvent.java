package com.strongholdmc.blevents.equipevents;
import com.strongholdmc.blcore.playerclasses.RPGArmor;
import com.strongholdmc.blcore.playerclasses.RPGItems;
import com.strongholdmc.blcore.playerclasses.RPGPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
/**
 * Created by wil on 6/19/2015.
 */
public class ChangeRPGArmorEvent extends Event
{
    private static final HandlerList handlers = new HandlerList();
    private int[] rawStats;
    private final ItemStack armor;
    private final RPGPlayer rpgPlayer;
    //TRUE: Item was added to armor slot
    //FALSE: Item was removed from armor slot
    private final boolean wasEquipped;

    public ChangeRPGArmorEvent(ItemStack armor, RPGPlayer rpgPlayer, boolean wasEquipped)
    {
        this.armor = armor;
        this.rpgPlayer = rpgPlayer;
        this.wasEquipped = wasEquipped;

        //Redundancy test
        if(!RPGArmor.isArmor(armor.getItemMeta().getLore()))
        {
            RPGArmor.makeRPGArmor(armor);
        }
    }

    public ItemStack getArmor()
    {
        return armor;
    }

    public RPGArmor getRPGArmor()
    {
        return RPGItems.getArmor(RPGArmor.getSerializableString(armor));
    }

    public RPGPlayer getRPGPlayer()
    {
        return rpgPlayer;
    }

    public boolean wasEquipped()
    {
        return wasEquipped;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList()
    {
        return handlers;
    }
}
