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
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "orbit", description = "POW!!! Right in the kisser! One of these days Alice, straight to the Moon - Sends the specified player into orbit.",
        usage = "/<command> <target> [<<power> | stop>]")
public class OrbitCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length == 0) {
            return false;
        }

        Player player = getPlayer(args[0]);

        if (player == null) {
            msg(FreedomCommand.PLAYER_NOT_FOUND, ChatColor.RED);
            return true;
        }

        FPlayer playerdata = plugin.playerList.getPlayer(player);

        double strength = 10.0;

        if (args.length >= 2) {
            if (args[1].equalsIgnoreCase("stop")) {
                msg("Stopped orbiting " + player.getName());
                playerdata.stopOrbiting();
                return true;
            }

            try {
                strength = Math.max(1.0, Math.min(150.0, Double.parseDouble(args[1])));
            } catch (NumberFormatException ex) {
                msg(ex.getMessage(), ChatColor.RED);
                return true;
            }
        }

        FUtil.adminAction(sender.getName(), "Orbiting " + player.getName(), false);

        player.setGameMode(GameMode.SURVIVAL);
        playerdata.startOrbiting(strength);
        player.setVelocity(new Vector(0, strength, 0));

        plugin.punishmentList.logPunishment(new Punishment(player.getName(), FUtil.getIp(player), sender.getName(), PunishmentType.ORBIT, null));
        return true;
    }
}