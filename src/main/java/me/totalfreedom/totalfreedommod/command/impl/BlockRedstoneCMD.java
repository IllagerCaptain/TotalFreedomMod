package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "blockredstone", description = "Blocks redstone on the server.", usage = "/<command>", aliases = "bre")
public class BlockRedstoneCMD extends FreedomCommand {

    public boolean run(final CommandSender sender, final Player playerSender, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) {
        if (ConfigEntry.ALLOW_REDSTONE.getBoolean()) {
            ConfigEntry.ALLOW_REDSTONE.setBoolean(false);
            FUtil.adminAction(sender.getName(), "Blocking all redstone", true);
            new BukkitRunnable() {
                public void run() {
                    if (!ConfigEntry.ALLOW_REDSTONE.getBoolean()) {
                        FUtil.adminAction("TotalFreedom", "Unblocking all redstone", false);
                        ConfigEntry.ALLOW_REDSTONE.setBoolean(true);
                    }
                }
            }.runTaskLater(plugin, 6000L);
        } else {
            ConfigEntry.ALLOW_REDSTONE.setBoolean(true);
            FUtil.adminAction(sender.getName(), "Unblocking all redstone", true);
        }
        return true;
    }
}