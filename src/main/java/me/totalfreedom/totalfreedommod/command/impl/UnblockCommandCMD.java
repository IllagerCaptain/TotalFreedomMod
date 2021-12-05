package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.player.FPlayer;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "unblock", description = "Unblocks commands for a player.", usage = "/<command> <player>", aliases = "unblockcommand,unblockcommands,ubcmds,unblockcmds,ubc")
public class UnblockCommandCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length == 0) {
            return false;
        }

        Player player = getPlayer(args[0]);
        if (player == null) {
            msg(PLAYER_NOT_FOUND);
            return true;
        }

        FPlayer fPlayer = plugin.playerList.getPlayer(player);
        if (fPlayer.allCommandsBlocked()) {
            fPlayer.setCommandsBlocked(false);
            FUtil.adminAction(sender.getName(), "Unblocking all commands for " + player.getName(), true);
            msg("Unblocked commands for " + player.getName() + ".");
        } else {
            msg("That players commands aren't blocked.", ChatColor.RED);
        }
        return true;
    }
}