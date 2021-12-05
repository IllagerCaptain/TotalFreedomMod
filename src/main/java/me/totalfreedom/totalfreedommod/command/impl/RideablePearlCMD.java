package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.shop.ShopItem;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "rideablepearl", description = "Obtain a rideable ender pearl", usage = "/<command>")
public class RideablePearlCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (plugin.playerList.getData(playerSender).hasItem(ShopItem.RIDEABLE_PEARL)) {
            playerSender.getInventory().addItem(plugin.shop.getRideablePearl());
            msg("You have been given a Rideable Ender Pearl", ChatColor.GREEN);
        } else {
            msg("You do not own a Rideable Ender Pearl! Purchase one from the shop.", ChatColor.RED);
        }
        return true;
    }
}
