package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.player.FPlayer;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "adminchat", description = "Talk privately with other admins on the server.", usage = "/<command> [message]", aliases = "o,sc,ac,staffchat")
public class AdminChatCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length == 0) {
            if (senderIsConsole) {
                msg("You must be in-game to toggle admin chat, it cannot be toggled via CONSOLE or Telnet.");
                return true;
            }

            FPlayer userinfo = plugin.playerList.getPlayer(playerSender);
            userinfo.setAdminChat(!userinfo.inAdminChat());
            msg("Admin chat turned " + (userinfo.inAdminChat() ? "on" : "off") + ".");
        } else {
            plugin.chatManager.adminChat(sender, StringUtils.join(args, " "));
        }
        return true;
    }
}