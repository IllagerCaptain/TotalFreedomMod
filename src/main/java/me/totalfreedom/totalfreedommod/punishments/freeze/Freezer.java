package me.totalfreedom.totalfreedommod.punishments.freeze;

import me.totalfreedom.totalfreedommod.services.AbstractService;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerMoveEvent;

public class Freezer extends AbstractService
{

    private boolean globalFreeze = false;

    @Override
    public void onStart()
    {
        globalFreeze = false;
    }

    @Override
    public void onStop()
    {
    }

    public void purge()
    {
        this.globalFreeze = false;

        for (Player player : server.getOnlinePlayers())
        {
            plugin.playerList.getPlayer(player).getFreezeData().setFrozen(false);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerMove(PlayerMoveEvent event)
    {
        final Player player = event.getPlayer();

        if (plugin.adminList.isAdmin(player))
        {
            return;
        }

        final FreezeData fd = plugin.playerList.getPlayer(player).getFreezeData();
        if (!fd.isFrozen() && !globalFreeze)
        {
            return;
        }

        FUtil.setFlying(player, true);

        Location loc = player.getLocation();

        event.setTo(loc);
    }

    public boolean isGlobalFreeze()
    {
        return globalFreeze;
    }

    public void setGlobalFreeze(boolean frozen)
    {
        this.globalFreeze = frozen;
    }
}