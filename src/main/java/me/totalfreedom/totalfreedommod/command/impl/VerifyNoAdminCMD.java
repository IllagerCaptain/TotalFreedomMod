package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.player.FPlayer;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "verifynoadmin", description = "Verify an admin without giving them admin permissions.", usage = "/<command> <player>", aliases = "vns,verifynostaff,vna")
public class VerifyNoAdminCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length < 1) {
            return false;
        }

        Player player = getPlayer(args[0]);

        if (player == null) {
            msg(PLAYER_NOT_FOUND);
            return true;
        }

        if (plugin.adminList.isAdminImpostor(player)) {
            String ip = FUtil.getIp(player);
            if (!plugin.adminList.verifiedNoAdmin.containsKey(player.getName())) {
                List<String> ips = new ArrayList<>();
                ips.add(ip);
                plugin.adminList.verifiedNoAdmin.put(player.getName(), ips);
            } else {
                List<String> ips = plugin.adminList.verifiedNoAdmin.get(player.getName());
                if (!ips.contains(ip)) {
                    ips.add(ip);
                    plugin.adminList.verifiedNoAdmin.remove(player.getName());
                    plugin.adminList.verifiedNoAdmin.put(player.getName(), ips);
                }
            }
            plugin.rankManager.updateDisplay(player);
            FUtil.adminAction(sender.getName(), "Verified " + player.getName() + ", without admin permissions.", true);
            player.setOp(true);
            msg(player, YOU_ARE_OP);
            final FPlayer fPlayer = plugin.playerList.getPlayer(player);
            if (fPlayer.getFreezeData().isFrozen()) {
                fPlayer.getFreezeData().setFrozen(false);
                msg(player, "You have been unfrozen.");
            }
            msg("Verified " + player.getName() + " but didn't give them admin permissions", ChatColor.GREEN);
        } else {
            msg(player.getName() + " is not an admin imposter.", ChatColor.RED);
        }

        return true;
    }

    @Override
    public List<String> getTabCompleteOptions(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> adminImposters = new ArrayList<>();
            for (Player player : server.getOnlinePlayers()) {
                if (plugin.adminList.isAdminImpostor(player)) {
                    adminImposters.add(player.getName());
                }
            }
            return adminImposters;
        }

        return Collections.emptyList();
    }
}