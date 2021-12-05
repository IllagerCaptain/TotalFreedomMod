package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.admin.Admin;
import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "commandspy", description = "Spy on commands", usage = "/<command>", aliases = "cmdspy")
public class CommandSpyCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        Admin admin = plugin.adminList.getAdmin(playerSender);
        admin.setCommandSpy(!admin.getCommandSpy());
        msg("CommandSpy " + (admin.getCommandSpy() ? "enabled." : "disabled."));
        plugin.adminList.save(admin);
        plugin.adminList.updateTables();
        return true;
    }
}