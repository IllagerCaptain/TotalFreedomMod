package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.services.impl.LogViewer.LogsRegistrationMode;
import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "logs", description = "Register your connection with the TFM logviewer.", usage = "/<command> [off]")
public class LogsCMD extends FreedomCommand {

    @Override
    public boolean run(final CommandSender sender, final Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        LogsRegistrationMode mode = LogsRegistrationMode.ADD;
        if (args.length == 1 && "off".equalsIgnoreCase(args[0])) {
            mode = LogsRegistrationMode.DELETE;
        }
        plugin.logViewer.updateLogsRegistration(sender, playerSender, mode);

        return true;
    }
}
