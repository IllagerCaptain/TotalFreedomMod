package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "kicknoob", description = "Kick all non-admins on server.", usage = "/<command>", aliases = "kickall")
public class KickNoobCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        FUtil.adminAction(sender.getName(), "Disconnecting all non-admins", true);

        for (Player player : server.getOnlinePlayers()) {
            if (!plugin.adminList.isAdmin(player)) {
                player.kickPlayer(ChatColor.RED + "All non-admins were kicked by " + sender.getName() + ".");
            }
        }

        return true;
    }
}