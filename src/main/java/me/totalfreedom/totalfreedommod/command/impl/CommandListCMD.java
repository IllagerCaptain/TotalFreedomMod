package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@CommandPermissions(level = Rank.NON_OP, source = SourceType.BOTH)
@CommandParameters(name = "commandlist", description = "Show all commands for all server plugins.", usage = "/<command>", aliases = "cmdlist")
public class CommandListCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        List<String> commands = new ArrayList<>();

        for (Plugin targetPlugin : server.getPluginManager().getPlugins()) {
            try {
                PluginDescriptionFile desc = targetPlugin.getDescription();
                Map<String, Map<String, Object>> map = desc.getCommands();

                for (Entry<String, Map<String, Object>> entry : map.entrySet()) {
                    String command_name = entry.getKey();
                    commands.add(command_name);
                }
            } catch (Throwable ignored) {
            }
        }

        Collections.sort(commands);

        msg(StringUtils.join(commands, ", "));

        return true;
    }
}
