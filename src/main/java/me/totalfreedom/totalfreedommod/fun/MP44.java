package me.totalfreedom.totalfreedommod.fun;

import me.totalfreedom.totalfreedommod.services.AbstractService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerQuitEvent;

public class MP44 extends AbstractService
{
    @Override
    public void onStart()
    {
    }

    @Override
    public void onStop()
    {
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        plugin.playerList.getPlayer(event.getPlayer()).disarmMP44();
    }
}