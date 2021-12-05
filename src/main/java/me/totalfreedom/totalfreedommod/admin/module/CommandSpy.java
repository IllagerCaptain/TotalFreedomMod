package me.totalfreedom.totalfreedommod.admin.module;

import me.totalfreedom.totalfreedommod.services.AbstractService;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandSpy extends AbstractService
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
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        for (Player player : server.getOnlinePlayers())
        {
            if (plugin.adminList.isAdmin(player) && plugin.adminList.getAdmin(player).getCommandSpy())
            {
                if (player != event.getPlayer())
                {
                    FUtil.playerMsg(player, event.getPlayer().getName() + ": " + event.getMessage());
                }
            }
        }
    }
}