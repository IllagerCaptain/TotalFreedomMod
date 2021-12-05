package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "say", description = "Broadcasts the given message as the server, includes sender name.", usage = "/<command> <message>")
public class SayCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length == 0) {
            return false;
        }

        String message = StringUtils.join(args, " ");

        if (senderIsConsole && FUtil.isFromHostConsole(sender.getName())) {
            if (message.equalsIgnoreCase("WARNING: Server is restarting, you will be kicked")) {
                FUtil.bcastMsg("Server is going offline.", ChatColor.GRAY);

                for (Player player : server.getOnlinePlayers()) {
                    player.kickPlayer(ChatColor.LIGHT_PURPLE + "Server is going offline, come back in about 20 seconds.");
                }

                server.shutdown();

                return true;
            }
        }

        FUtil.bcastMsg(String.format("[Server:%s] %s", sender.getName(), message), ChatColor.LIGHT_PURPLE);
        plugin.discord.messageChatChannel(String.format("[Server:%s] \u00BB %s", sender.getName(), message));
        return true;
    }
}