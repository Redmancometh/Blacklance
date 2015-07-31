package com.strongholdmc.blmerchants.trait;
import org.bukkit.entity.Entity;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.util.DataKey;

public class BLMerchant extends Trait
{
    DataKey key;
    private long id;
    public BLMerchant()
    {
	super("BLMerchant");
    }

    public void load(DataKey key)
    {
	id = key.getInt("id");
    }
    
    public void setID(long id)
    {
	this.id=id;
    }
    
    public long getID()
    {
	return id;
    }
    
    public void save(DataKey key)
    {
	key.setLong("id", this.id);
    }

    public void onSpawn()
    {
	
    }
    @SuppressWarnings("deprecation")
    public Entity getBukkitEntity()
    {
	return this.npc.getBukkitEntity();
    }
}
