package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.player.FPlayer;
import me.totalfreedom.totalfreedommod.punishments.Punishment;
import me.totalfreedom.totalfreedommod.punishments.PunishmentType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "mute", description = "Mutes a player with brute force.", usage = "/<command> <[-s | -q] <player> [reason] | list | purge | all>", aliases = "stfu")
public class MuteCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length == 0) {
            return false;
        }

        if (args[0].equalsIgnoreCase("list")) {
            msg("Muted players:");
            FPlayer info;
            int count = 0;
            for (Player mp : server.getOnlinePlayers()) {
                info = plugin.playerList.getPlayer(mp);
                if (info.isMuted()) {
                    msg("- " + mp.getName());
                    count++;
                }
            }
            if (count == 0) {
                msg("- none");
            }

            return true;
        }

        if (args[0].equalsIgnoreCase("purge")) {
            FUtil.adminAction(sender.getName(), "Unmuting all players.", true);
            FPlayer info;
            int count = 0;
            for (Player mp : server.getOnlinePlayers()) {
                info = plugin.playerList.getPlayer(mp);
                if (info.isMuted()) {
                    info.setMuted(false);
                    mp.sendTitle(ChatColor.RED + "You've been unmuted.", ChatColor.YELLOW + "Be sure to follow the rules!", 20, 100, 60);
                    count++;
                }
            }
            plugin.muter.MUTED_PLAYERS.clear();
            msg("Unmuted " + count + " players.");
            return true;
        }

        if (args[0].equalsIgnoreCase("all")) {
            FUtil.adminAction(sender.getName(), "Muting all non-admins", true);

            FPlayer playerdata;
            int counter = 0;
            for (Player player : server.getOnlinePlayers()) {
                if (!plugin.adminList.isAdmin(player)) {
                    player.sendTitle(ChatColor.RED + "You've been muted globally.", ChatColor.YELLOW + "Please be patient and you will be unmuted shortly.", 20, 100, 60);
                    playerdata = plugin.playerList.getPlayer(player);
                    playerdata.setMuted(true);
                    counter++;
                }
            }

            msg("Muted " + counter + " players.");
            return true;
        }

        // -s option (smite)
        boolean smite = args[0].equals("-s");
        // -q option (shadowmute)
        boolean quiet = args[0].equals("-q");
        if (smite || quiet) {
            args = ArrayUtils.subarray(args, 1, args.length);

            if (args.length < 1) {
                return false;
            }
        }

        final Player player = getPlayer(args[0]);
        if (player == null) {
            msg(PLAYER_NOT_FOUND);
            return true;
        }

        String reason = null;
        if (args.length > 1) {
            reason = StringUtils.join(args, " ", 1, args.length);
        }

        FPlayer playerdata = plugin.playerList.getPlayer(player);
        if (plugin.adminList.isAdmin(player)) {
            msg(player.getName() + " is an admin, and can't be muted.");
            return true;
        }

        if (!playerdata.isMuted()) {
            playerdata.setMuted(true);
            player.sendTitle(ChatColor.RED + "You've been muted.", ChatColor.YELLOW + "Be sure to follow the rules!", 20, 100, 60);
            if (reason != null) {
                msg(player, ChatColor.RED + "Reason: " + ChatColor.YELLOW + reason);
            }
            if (quiet) {
                msg("Muted " + player.getName() + " quietly");
                return true;
            }

            FUtil.adminAction(sender.getName(), "Muting " + player.getName(), true);

            if (smite) {
                SmiteCMD.smite(sender, player, reason);
            }

            msg(player, "You have been muted by " + ChatColor.YELLOW + sender.getName(), ChatColor.RED);
            msg("Muted " + player.getName());

            plugin.punishmentList.logPunishment(new Punishment(player.getName(), FUtil.getIp(player), sender.getName(), PunishmentType.MUTE, reason));
        } else {
            msg(ChatColor.RED + "That player is already muted.");
        }

        return true;
    }

    @Override
    public List<String> getTabCompleteOptions(CommandSender sender, Command command, String alias, String[] args) {
        if (!plugin.adminList.isAdmin(sender)) {
            return null;
        }

        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();
            arguments.addAll(FUtil.getPlayerList());
            arguments.addAll(Arrays.asList("list", "purge", "all"));
            return arguments;
        }

        return Collections.emptyList();
    }
}