package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "explosivearrow", description = "Make arrows explode", usage = "/<command>", aliases = "ea")
public class ExplosiveArrowCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        boolean onList = plugin.itemFun.explosivePlayers.contains(playerSender);
        if (onList) {
            plugin.itemFun.explosivePlayers.remove(playerSender);
            msg("You no longer have explosive arrows", ChatColor.RED);
        } else {
            plugin.itemFun.explosivePlayers.add(playerSender);
            msg("You now have explosive arrows", ChatColor.GREEN);
        }

        return true;
    }
}