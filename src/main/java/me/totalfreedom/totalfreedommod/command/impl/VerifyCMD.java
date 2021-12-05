package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.discord.Discord;
import me.totalfreedom.totalfreedommod.player.FPlayer;
import me.totalfreedom.totalfreedommod.player.PlayerData;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.IMPOSTOR, source = SourceType.BOTH)
@CommandParameters(name = "verify", description = "Sends a verification code to the player, or the player can input the sent code. Admins can manually verify a player impostor.", usage = "/<command> [-m] <code | <playername>>")
public class VerifyCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        boolean verificationEnabled = ConfigEntry.DISCORD_VERIFICATION.getBoolean();
        if (!plugin.discord.enabled) {
            msg("The Discord verification system is currently disabled.", ChatColor.RED);
            return true;
        }

        if (!verificationEnabled) {
            msg("The Discord verification system is currently disabled.", ChatColor.RED);
            return true;
        }

        if (!plugin.playerList.isImpostor(playerSender)) {
            msg("You are not an impostor, therefore you do not need to verify.", ChatColor.RED);
            return true;
        }

        PlayerData playerData = plugin.playerList.getData(playerSender);
        String discordId = playerData.getDiscordID();

        if (playerData.getDiscordID() == null) {
            msg("You do not have a Discord account linked to your Minecraft account, please verify the manual way.", ChatColor.RED);
            return true;
        }

        if (args.length == 0) {
            String code = plugin.discord.generateCode(10);
            plugin.discord.addVerificationCode(code, playerData);
            plugin.discord.getUser(discordId).openPrivateChannel().complete().sendMessage("A user with the IP `" + FUtil.getIp(playerSender) + "` has sent a verification request. Please run the following in-game command: `/verify " + code + "`").complete();
            msg("A verification code has been sent to your account, please copy the code and run /verify <code>", ChatColor.GREEN);
            return true;
        }

        if (args[0].equalsIgnoreCase("-m")) {
            if (!senderIsConsole)
            {
                if (!plugin.rankManager.getRank(playerSender).isAtLeast(Rank.ADMIN))
                {
                    msg(NO_PERMISSION);
                    return true;
                }
            }
            if (args.length != 2)
            {
                msg("Usage: /verify -m <player>", ChatColor.RED);
                return true;
            }
            final Player target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                msg(PLAYER_NOT_FOUND);
                return true;
            }

            if (!plugin.playerList.isImpostor(target)) {
                msg("That player is not an impostor.");
                return true;
            }
            FUtil.adminAction(sender.getName(), "Manually verifying player " + target.getName(), false);
            target.setOp(true);
            msg(target, YOU_ARE_OP);

            if (plugin.playerList.getPlayer(target).getFreezeData().isFrozen()) {
                plugin.playerList.getPlayer(target).getFreezeData().setFrozen(false);
                msg(target, "You have been unfrozen.");
            }

            plugin.playerList.verify(target, null);
            plugin.rankManager.updateDisplay(target);
            return true;
        }

        String code = args[0];
        String backupCode = null;

        if (plugin.playerList.isImpostor(playerSender)) {
            PlayerData mapPlayer = plugin.discord.getVerificationCodes().get(code);
            if (mapPlayer == null) {
                if (!playerData.getBackupCodes().contains(Discord.getMD5(code))) {
                    msg("You have entered an invalid verification code", ChatColor.RED);
                    return true;
                } else {
                    backupCode = Discord.getMD5(code);
                }
            } else {
                plugin.discord.removeVerificationCode(code);
            }

            final FPlayer fPlayer = plugin.playerList.getPlayer(playerSender);
            if (fPlayer.getFreezeData().isFrozen()) {
                fPlayer.getFreezeData().setFrozen(false);
                msg("You have been unfrozen.");
            }
            FUtil.bcastMsg(playerSender.getName() + " has verified!", ChatColor.GOLD);
            playerSender.setOp(true);
            plugin.playerList.verify(playerSender, backupCode);
            return true;
        }
        return true;
    }
}