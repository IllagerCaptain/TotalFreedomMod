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
@CommandParameters(name = "lightningrod", description = "Obtain a lightning rod", usage = "/<command>")
public class LightningRodCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (plugin.playerList.getData(playerSender).hasItem(ShopItem.LIGHTNING_ROD)) {
            playerSender.getInventory().addItem(plugin.shop.getLightningRod());
            msg("You have been given a Lightning Rod", ChatColor.GREEN);
        } else {
            msg("You do not own a Lightning Rod! Purchase one from the shop.", ChatColor.RED);
        }
        return true;
    }
}
