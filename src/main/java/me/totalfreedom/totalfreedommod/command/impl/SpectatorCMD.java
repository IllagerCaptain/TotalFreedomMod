package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "spectator", description = "Quickly change your own gamemode to spectator, or define someone's username to change theirs.", usage = "/<command> <[partialname]>", aliases = "gmsp")
public class SpectatorCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length == 0) {
            if (isConsole()) {
                msg("When used from the console, you must define a target player.");
                return true;
            }

            playerSender.setGameMode(GameMode.SPECTATOR);
            msg("Your gamemode has been set to spectator.");
            return true;
        }

        Player player = getPlayer(args[0]);

        if (player == null) {
            msg(PLAYER_NOT_FOUND);
            return true;
        }

        msg("Setting " + player.getName() + " to game mode spectator");
        msg(player, sender.getName() + " set your game mode to spectator");
        player.setGameMode(GameMode.SPECTATOR);
        return true;
    }
}