package me.totalfreedom.totalfreedommod.services;

import java.util.logging.Logger;

import me.totalfreedom.totalfreedommod.TotalFreedomMod;
import me.totalfreedom.totalfreedommod.util.FLog;
import org.bukkit.Server;
import org.bukkit.event.Listener;

public abstract class AbstractService implements Listener
{
    protected final TotalFreedomMod plugin;
    protected final Server server;
    protected final Logger logger;

    public AbstractService()
    {
        plugin = TotalFreedomMod.getPlugin();
        server = plugin.getServer();
        logger = FLog.getPluginLogger();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.serviceHandler.add(this);
    }

    public abstract void onStart();

    public abstract void onStop();
}
