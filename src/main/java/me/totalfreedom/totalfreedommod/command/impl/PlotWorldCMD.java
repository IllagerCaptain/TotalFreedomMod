package me.totalfreedom.totalfreedommod.command.impl;

import io.papermc.lib.PaperLib;
import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.NON_OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "plotworld", description = "Go to the PlotWorld.", usage = "/<command>", aliases = "pw")
public class PlotWorldCMD extends FreedomCommand {
    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        World plotworld = server.getWorld("plotworld");
        if (plotworld != null) {
            PaperLib.teleportAsync(playerSender, plotworld.getSpawnLocation());
        } else {
            msg("\"plotworld\" doesn't exist.");
        }
        return true;
    }
}
