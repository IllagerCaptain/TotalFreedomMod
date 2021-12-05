package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Displayable;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.NON_OP, source = SourceType.BOTH)
@CommandParameters(name = "rank", description = "Show the rank of the sender or a specified user.", usage = "/<command> [player]")
public class RankCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (senderIsConsole && args.length == 0) {
            for (Player player : server.getOnlinePlayers()) {
                msg(message(player));
            }
            return true;
        }

        if (args.length == 0) {
            msg(message(playerSender));
            return true;
        }

        if (args.length > 1) {
            return false;
        }

        final Player player = getPlayer(args[0], true);

        if (player == null) {
            msg(PLAYER_NOT_FOUND);
            return true;
        }

        msg(message(player));

        return true;
    }

    public String message(Player player) {
        Displayable display = plugin.rankManager.getDisplay(player);
        Rank rank = plugin.rankManager.getRank(player);

        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.AQUA)
                .append(player.getName())
                .append(" is ")
                .append(display.getColoredLoginMessage());

        if (rank != display) {
            sb.append(ChatColor.AQUA)
                    .append(" (")
                    .append(rank.getColoredName())
                    .append(ChatColor.AQUA)
                    .append(')');
        }

        return sb.toString();
    }
}