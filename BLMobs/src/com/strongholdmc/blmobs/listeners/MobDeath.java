package com.strongholdmc.blmobs.listeners;
import org.bukkit.ChatColor;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import com.gmail.filoghost.holograms.api.Hologram;
import com.gmail.filoghost.holograms.api.HolographicDisplaysAPI;
import com.strongholdmc.blcore.main.BlackLance;
import com.strongholdmc.blevents.combatevents.MobDeathEvent;

@SuppressWarnings("deprecation")
public class MobDeath implements Listener
{
    @EventHandler
    public void mobDeath(MobDeathEvent e)
    {
	new BukkitRunnable()
	{
	    public void run()
	    {
		e.getKilled().getBukkitEntity().playEffect(EntityEffect.DEATH);
		Location loc1 = e.getKilled().getBukkitEntity().getLocation();
		final Hologram hologram = HolographicDisplaysAPI.createIndividualHologram(BlackLance.getPlugin(), loc1.add(0, 1, 0), e.getAttacker(), ChatColor.GREEN + "XP: " + e.getExperience());
		hologram.setLocation(loc1.add(0, 1, 0));
		new BukkitRunnable()
		{
		    public void run()
		    {
			hologram.delete();
			e.getKilled().destroy();
		    }
		}.runTaskLater(BlackLance.getPlugin(), 15);
	    }
	}.runTask(BlackLance.getPlugin());
    }
}
