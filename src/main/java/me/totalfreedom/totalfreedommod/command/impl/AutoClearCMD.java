package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "autoclear", description = "Toggle whether or not a player has their inventory automatically cleared when they join", usage = "/<command> <player>")
public class AutoClearCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length == 0) {
            return false;
        }

        boolean enabled = plugin.loginProcess.CLEAR_ON_JOIN.contains(args[0]);

        if (enabled) {
            plugin.loginProcess.CLEAR_ON_JOIN.remove(args[0]);
        } else {
            plugin.loginProcess.CLEAR_ON_JOIN.add(args[0]);
        }

        msg(args[0] + " will " + (enabled ? "no longer" : "now") + " have their inventory cleared when they join.");

        return true;
    }
}
