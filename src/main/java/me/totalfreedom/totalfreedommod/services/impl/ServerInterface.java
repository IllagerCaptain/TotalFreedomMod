package me.totalfreedom.totalfreedommod.services.impl;

import me.totalfreedom.totalfreedommod.services.AbstractService;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Set;

public class ServerInterface extends AbstractService
{

    @Override
    public void onStart()
    {
    }

    @Override
    public void onStop()
    {
    }

    public int purgeWhitelist()
    {
        Set<OfflinePlayer> whitelisted = Bukkit.getServer().getWhitelistedPlayers();

        whitelisted.forEach(player -> player.setWhitelisted(false));

        return whitelisted.size();
    }

    public boolean isWhitelisted()
    {
        return Bukkit.hasWhitelist();
    }

    public boolean isWhitelisted(Player player)
    {
        return player.isWhitelisted();
    }

    public String getVersion()
    {
        return Bukkit.getBukkitVersion();
    }

}
