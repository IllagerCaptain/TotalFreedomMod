package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "setcompass", description = "Set your compass to the specified position.", usage = "/<command> <x> <y> <z>")
public class SetCompassCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length < 3) {
            return false;
        }

        try {
            Location location = new Location(playerSender.getWorld(), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            playerSender.setCompassTarget(location);
            msg("Successfully set your compass coordinates to X: " + args[0] + ", Y: " + args[1] + ", Z: " + args[2] + ".", ChatColor.GREEN);
        } catch (NumberFormatException e) {
            msg("One or more of your coordinates are not a valid integer.", ChatColor.RED);
        }

        return true;
    }
}