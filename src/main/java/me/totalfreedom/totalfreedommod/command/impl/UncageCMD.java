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
@CommandParameters(name = "uncage", description = "Uncage a player", usage = "/<command> <name>")
public class UncageCMD extends FreedomCommand {

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

        final FPlayer fPlayer = plugin.playerList.getPlayer(player);
        if (fPlayer.getCageData().isCaged()) {
            FUtil.adminAction(sender.getName(), "Uncaging " + player.getName(), true);
            fPlayer.getCageData().setCaged(false);
        } else {
            msg("That player is not caged!", ChatColor.RED);
        }
        return true;
    }
}
