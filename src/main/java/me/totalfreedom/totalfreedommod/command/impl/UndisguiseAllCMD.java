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
@CommandParameters(name = "undisguiseall", description = "Undisguise all online players on the server", usage = "/<command> [-a]", aliases = "uall")
public class UndisguiseAllCMD extends FreedomCommand {
    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (!plugin.libsDisguisesBridge.isEnabled()) {
            msg("LibsDisguises is not enabled.");
            return true;
        }

        boolean admins = args.length > 0 && args[0].equalsIgnoreCase("-a");

        FUtil.adminAction(sender.getName(), "Undisguising all " + (admins ? "players" : "non-admins"), true);

        plugin.libsDisguisesBridge.undisguiseAll(admins);

        return true;
    }
}