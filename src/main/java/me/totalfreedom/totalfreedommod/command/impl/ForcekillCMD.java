package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.BOTH)
@CommandParameters(name = "forcekill", description = "Forcefully kill someone - for those who REALLY need to die.", usage = "/<command> <playername>")
public class ForcekillCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (!plugin.adminList.isAdmin(sender) && !senderIsConsole) {
            playerSender.setHealth(0);
            return true;
        }

        if (args.length < 1) {
            return false;
        }

        final Player player = getPlayer(args[0]);
        if (player == null) {
            msg(PLAYER_NOT_FOUND);
            return true;
        }

        player.setHealth(0);
        return true;
    }
}