package com.strongholdmc.blcore.listeners;
import net.citizensnpcs.api.event.NPCDeathEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
public class DropListeners implements Listener
{
    @EventHandler(priority = EventPriority.MONITOR)
    public void stopXP(PlayerDeathEvent event)
    {
	event.setDroppedExp(0);
    }

    @EventHandler
    public void stopXP2(NPCDeathEvent event)
    {
        if (event.getDroppedExp() > 0)
        {
            event.setDroppedExp(0);
        }
    }
}
