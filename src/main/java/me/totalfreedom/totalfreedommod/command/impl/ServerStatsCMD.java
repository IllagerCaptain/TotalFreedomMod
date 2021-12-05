package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.BOTH)
@CommandParameters(name = "serverstats", description = "Check the status of the server, including opped players, admins, etc.", usage = "/<command>", aliases = "ss")
public class ServerStatsCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        msg("-==" + ConfigEntry.SERVER_NAME.getString() + " server stats==-", ChatColor.GOLD);
        msg("Total opped players: " + server.getOperators().size(), ChatColor.RED);
        msg("Total admins: " + plugin.adminList.getAllAdmins().size() + " (" + plugin.adminList.getActiveAdmins().size() + " active)", ChatColor.BLUE);
        int bans = plugin.indefiniteBanList.getIndefBans().size();
        int nameBans = plugin.indefiniteBanList.getNameBanCount();
        int uuidBans = plugin.indefiniteBanList.getUuidBanCount();
        int ipBans = plugin.indefiniteBanList.getIpBanCount();
        msg("Total indefinite ban entries: " + bans + " (" + nameBans + " name bans, " + uuidBans + " UUID bans, and " + ipBans + " IP bans)", ChatColor.GREEN);
        return true;
    }
}