package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.punishments.freeze.FreezeData;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "freeze", description = "Freeze/Unfreeze a specified player, or all non-admins on the server.", usage = "/<command> [target | purge]", aliases = "fr")
public class FreezeCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length == 0) {
            boolean gFreeze = !plugin.freezer.isGlobalFreeze();
            plugin.freezer.setGlobalFreeze(gFreeze);

            if (!gFreeze) {
                FUtil.adminAction(sender.getName(), "Unfreezing all players", false);
                msg("Players are now free to move.");
                return true;
            }

            FUtil.adminAction(sender.getName(), "Freezing all players", false);
            for (Player player : server.getOnlinePlayers()) {
                if (!isAdmin(player)) {
                    player.sendTitle(ChatColor.RED + "You've been globally frozen.", ChatColor.YELLOW + "Please be patient and you will be unfrozen shortly.", 20, 100, 60);
                    msg(player, "You have been globally frozen due to an OP breaking the rules, please wait and you will be unfrozen soon.", ChatColor.RED);
                }
            }
            msg("Players are now frozen.");
            return true;
        }

        if (args[0].equalsIgnoreCase("purge")) {
            FUtil.adminAction(sender.getName(), "Unfreezing all players", false);
            for (Player player : server.getOnlinePlayers()) {
                if (!isAdmin(player)) {
                    player.sendTitle(ChatColor.GREEN + "You've been unfrozen.", ChatColor.YELLOW + "You may now move again.", 20, 100, 60);
                }
            }
            plugin.freezer.purge();
            return true;
        }

        final Player player = getPlayer(args[0]);

        if (player == null) {
            msg(FreedomCommand.PLAYER_NOT_FOUND, ChatColor.RED);
            return true;
        }

        final FreezeData fd = plugin.playerList.getPlayer(player).getFreezeData();
        fd.setFrozen(!fd.isFrozen());

        msg(player.getName() + " has been " + (fd.isFrozen() ? "frozen" : "unfrozen") + ".");
        msg(player, "You have been " + (fd.isFrozen() ? "frozen" : "unfrozen") + ".", ChatColor.AQUA);
        return true;
    }
}