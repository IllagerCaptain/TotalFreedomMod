package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "nicknyan", description = "Essentials Interface Command - Randomize the colors of your nickname.", usage = "/<command> <<nick> | off>")
public class NickNyanCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length != 1) {
            return false;
        }

        if (args[0].equalsIgnoreCase("off")) {
            if (plugin.essentialsBridge.isEnabled())
                plugin.essentialsBridge.setNickname(sender.getName(), null);
            else ((Player) sender).setDisplayName(null);
            msg("Nickname cleared.");
            return true;
        }

        final String nickPlain = ChatColor.stripColor(FUtil.colorize(args[0].trim()));

        if (!nickPlain.matches("^[a-zA-Z_0-9" + ChatColor.COLOR_CHAR + "]+$")) {
            msg("That nickname contains invalid characters.");
            return true;
        } else if (nickPlain.length() < 3 || nickPlain.length() > 30) {
            msg("Your nickname must be between 3 and 30 characters long.");
            return true;
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player == playerSender) {
                continue;
            }
            if (player.getName().equalsIgnoreCase(nickPlain) || ChatColor.stripColor(player.getDisplayName()).trim().equalsIgnoreCase(nickPlain)) {
                msg("That nickname is already in use.");
                return true;
            }
        }

        final StringBuilder newNick = new StringBuilder();

        final char[] chars = nickPlain.toCharArray();
        for (char c : chars) {
            newNick.append(FUtil.randomChatColor()).append(c);
        }

        newNick.append(ChatColor.WHITE);

        if (plugin.essentialsBridge.isEnabled())
            plugin.essentialsBridge.setNickname(sender.getName(), newNick.toString());
        else {
            ((Player) sender).setDisplayName(newNick.toString());
        }

        msg("Your nickname is now: " + newNick);
        return true;
    }
}