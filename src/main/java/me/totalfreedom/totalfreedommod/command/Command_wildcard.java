package me.totalfreedom.totalfreedommod.command;

import java.util.Arrays;
import java.util.List;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.SUPER_ADMIN, source = SourceType.BOTH, blockHostConsole = true)
@CommandParameters(description = "Run any command on all users, username placeholder = ?.", usage = "/<command> [fluff] ? [fluff] ?")
public class Command_wildcard extends FreedomCommand
{

    public static final List<String> BLOCKED_COMMANDS = Arrays.asList(
            "wildcard",
            "gtfo",
            "doom",
            "saconfig",
            "smite"
    );

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length == 0)
        {
            return false;
        }

        FreedomCommand command = plugin.cl.getByName(args[0]);
        if (command == null)
        {
            msg("Unknown command: " + args[0], ChatColor.RED);
            return true;
        }

        for (String blockedCommand : BLOCKED_COMMANDS)
        {
            if (blockedCommand.equals(command.getName()) || command.getAliases().contains(blockedCommand))
            {
                msg("Did you really think that was going to work?", ChatColor.RED);
                return true;
            }
        }

        String baseCommand = StringUtils.join(args, " ");

        if (plugin.cb.isCommandBlocked(baseCommand, sender))
        {
            // CommandBlocker handles messages and broadcasts
            return true;
        }

        for (Player player : server.getOnlinePlayers())
        {
            String runCommand = baseCommand.replaceAll("\\x3f", player.getName());
            msg("Running Command: " + runCommand);
            server.dispatchCommand(sender, runCommand);
        }

        return true;
    }
}
