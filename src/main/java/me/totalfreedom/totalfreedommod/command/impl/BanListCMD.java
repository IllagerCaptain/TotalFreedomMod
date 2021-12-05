package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.BOTH)
@CommandParameters(name = "banlist", description = "Shows all banned player names. Admins may optionally use 'purge' to clear the list.", usage = "/<command> [purge]")
public class BanListCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("purge")) {
                checkRank(Rank.SENIOR_ADMIN);

                FUtil.adminAction(sender.getName(), "Purging the ban list", true);
                int amount = plugin.banManager.purge();
                msg("Purged " + amount + " player bans.");
                return true;
            }
            return false;
        }

        msg(plugin.banManager.getAllBans().size() + " player bans ("
                + plugin.banManager.getUsernameBans().size() + " usernames, "
                + plugin.banManager.getIpBans().size() + " IPs)");
        return true;
    }
}