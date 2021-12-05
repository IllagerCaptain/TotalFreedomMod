package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.punishments.banning.Ban;
import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FLog;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH, blockHostConsole = true)
@CommandParameters(name = "banip", description = "Bans the specified ip.", usage = "/<command> <ip> [reason] [-q]")
public class BanIPCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length == 0) {
            return false;
        }

        boolean silent = false;

        String reason = null;

        String ip = args[0];

        if (FUtil.isValidIPv4(ip)) {
            msg(ip + " is not a valid IP address", ChatColor.RED);
            return true;
        }

        if (plugin.banManager.getByIp(ip) != null) {
            msg("The IP " + ip + " is already banned", ChatColor.RED);
            return true;
        }

        if (args[args.length - 1].equalsIgnoreCase("-q")) {
            silent = true;

            if (args.length >= 2) {
                reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length - 1), " ");
            }
        } else if (args.length > 1) {
            reason = StringUtils.join(ArrayUtils.subarray(args, 1, args.length), " ");
        }

        // Ban player
        Ban ban = Ban.forPlayerIp(ip, sender, null, reason);
        plugin.banManager.addBan(ban);

        // Kick player and handle others on IP
        for (Player player : server.getOnlinePlayers()) {
            if (FUtil.getIp(player).equals(ip)) {
                player.kickPlayer(ban.bakeKickMessage());
            }

            if (!silent) {
                // Broadcast
                FLog.info(ChatColor.RED + sender.getName() + " - Banned the IP " + ip);
                String message = sender.getName() + " - Banned " + (plugin.adminList.isAdmin(player) ? "the IP " + ip : "an IP");
                msg(player, message, ChatColor.RED);
            }
        }

        return true;
    }
}