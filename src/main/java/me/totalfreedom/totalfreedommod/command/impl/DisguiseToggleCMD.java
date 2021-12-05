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
@CommandParameters(name = "disguisetoggle", description = "Toggle LibsDisguises for everyone online.", usage = "/<command>", aliases = "dtoggle")
public class DisguiseToggleCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (!plugin.libsDisguisesBridge.isEnabled()) {
            msg("LibsDisguises is not enabled.");
            return true;
        }

        FUtil.adminAction(sender.getName(), (plugin.libsDisguisesBridge.isDisguisesEnabled() ? "Disabling" : "Enabling") + " disguises", false);

        if (plugin.libsDisguisesBridge.isDisguisesEnabled()) {
            plugin.libsDisguisesBridge.undisguiseAll(true);
            plugin.libsDisguisesBridge.setDisguisesEnabled(false);
        } else {
            plugin.libsDisguisesBridge.setDisguisesEnabled(true);
        }

        msg("Disguises are now " + (plugin.libsDisguisesBridge.isDisguisesEnabled() ? "enabled." : "disabled."));
        return true;
    }
}