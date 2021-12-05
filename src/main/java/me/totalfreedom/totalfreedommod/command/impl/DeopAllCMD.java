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

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH, blockHostConsole = true)
@CommandParameters(name = "deopall", description = "Deop everyone on the server.", usage = "/<command>")
public class DeopAllCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        FUtil.adminAction(sender.getName(), "De-opping all players on the server", true);

        for (Player player : server.getOnlinePlayers()) {
            player.setOp(false);
            msg(player, YOU_ARE_NOT_OP);
            plugin.rankManager.updateDisplay(player);
        }

        return true;
    }
}
