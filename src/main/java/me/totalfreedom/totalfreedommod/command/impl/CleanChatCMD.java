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

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "cleanchat", description = "Clears the chat.", usage = "/<command>", aliases = "cc")
public class CleanChatCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        for (Player player : server.getOnlinePlayers()) {
            if (!plugin.adminList.isAdmin(player)) {
                for (int i = 0; i < 100; i++) {
                    msg(player, "");
                }
            }
        }
        FUtil.adminAction(sender.getName(), "Cleared chat", true);
        return true;
    }
}
