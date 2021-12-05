package me.totalfreedom.totalfreedommod.command.impl;

import com.earth2me.essentials.User;
import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.player.PlayerData;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "unban", description = "Unbans the specified player.", usage = "/<command> <username> [-r]")
public class UnbanCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length > 0) {
            String username;
            String ip;

            // Gets the IP using Essentials data if available
            if (plugin.essentialsBridge.isEnabled() && plugin.essentialsBridge.getEssentialsUser(args[0]) != null) {
                User essUser = plugin.essentialsBridge.getEssentialsUser(args[0]);
                //
                username = essUser.getName();
                ip = essUser.getLastLoginAddress();
            }
            // Secondary method - using Essentials if available
            else {
                final PlayerData entry = plugin.playerList.getData(args[0]);
                if (entry == null) {
                    msg(PLAYER_NOT_FOUND);
                    return true;
                }
                username = entry.getName();
                ip = entry.getIps().get(0);
            }

            FUtil.adminAction(sender.getName(), "Unbanning " + username, true);
            plugin.banManager.removeBan(plugin.banManager.getByUsername(username));
            plugin.banManager.removeBan(plugin.banManager.getByIp(ip));
            msg(username + " has been unbanned along with the IP: " + ip);

            if (args.length >= 2) {
                if (args[1].equalsIgnoreCase("-r")) {
                    plugin.coreProtectBridge.restore(username);
                    msg("Restored edits for: " + username);
                }
            }
            return true;
        }
        return false;
    }
}