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
@CommandParameters(name = "denick", description = "Essentials Interface Command - Remove the nickname of all players on the server.", usage = "/<command>")
public class DenickCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (!plugin.essentialsBridge.isEnabled()) {
            msg("Essentials is not enabled on this server.");
            return true;
        }

        FUtil.adminAction(sender.getName(), "Removing all nicknames", false);

        for (Player player : server.getOnlinePlayers()) {
            if (plugin.essentialsBridge.isEnabled())
                plugin.essentialsBridge.setNickname(player.getName(), null);
            else player.setDisplayName(null);
        }

        return true;
    }
}
