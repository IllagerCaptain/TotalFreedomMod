package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH, blockHostConsole = true)
@CommandParameters(name = "wildcard", description = "Run any command on all users, username placeholder = ?.", usage = "/<command> [fluff] ? [fluff] ?")
public class WildCardCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length == 0) {
            return false;
        }

        Command runCmd = server.getPluginCommand(args[0]);
        FreedomCommand fCmd = plugin.commandLoader.getByName(args[0]);
        boolean alias = plugin.commandLoader.isAlias(args[0]);
        if (runCmd == null && fCmd == null && !alias) {
            msg("Unknown command: " + args[0], ChatColor.RED);
            return true;
        }

        List<String> aliases = new ArrayList<>();

        if (runCmd != null) {
            aliases = runCmd.getAliases();
        }

        if (fCmd != null) {
            aliases = Arrays.asList(fCmd.getAliases().split(","));
        }

        for (String blockedCommand : ConfigEntry.WILDCARD_BLOCKED_COMMANDS.getStringList()) {
            if (blockedCommand.equals(args[0].toLowerCase()) || aliases.contains(blockedCommand)) {
                msg("Did you really think that was going to work?", ChatColor.RED);
                return true;
            }
        }

        String baseCommand = StringUtils.join(args, " ");

        if (plugin.commandBlocker.isCommandBlocked(baseCommand, sender)) {
            // CommandBlocker handles messages and broadcasts
            return true;
        }

        for (Player player : server.getOnlinePlayers()) {
            String runCommand = baseCommand.replaceAll("\\x3f", player.getName());
            msg("Running Command: " + runCommand);
            server.dispatchCommand(sender, runCommand);
        }

        return true;
    }
}
