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
@CommandParameters(name = "trail", description = "Trails rainbow wool behind you as you walk/fly.", usage = "/<command>")
public class TrailCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (!plugin.playerList.getData(playerSender).hasItem(ShopItem.RAINBOW_TRAIL)) {
            msg("You didn't purchase the ability to have a " + ShopItem.RAINBOW_TRAIL.getName() + "! Purchase it from the shop.", ChatColor.RED);
            return true;
        }

        if (plugin.trailer.contains(playerSender)) {
            plugin.trailer.remove(playerSender);
            msg("Trail disabled.");
        } else {
            plugin.trailer.add(playerSender);
            msg("Trail enabled. Run this command again to disable it.");
        }

        return true;
    }
}