package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.player.FPlayer;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "lastcommand", description = "Show the last command the specified player used.", usage = "/<command> <player>")
public class LastCommandCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length == 0) {
            return false;
        }

        final Player player = getPlayer(args[0]);

        if (player == null) {
            msg(FreedomCommand.PLAYER_NOT_FOUND);
            return true;
        }

        final FPlayer playerdata = plugin.playerList.getPlayer(player);

        if (playerdata != null) {
            String lastCommand = playerdata.getLastCommand();
            if (lastCommand.isEmpty()) {
                lastCommand = "(none)";
            }
            msg(player.getName() + " - Last Command: " + lastCommand, ChatColor.GRAY);
        }

        return true;
    }
}