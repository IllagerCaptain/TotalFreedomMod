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
@CommandParameters(name = "clownfish", description = "Obtain a clown fish", usage = "/<command>")
public class ClownFishCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (plugin.playerList.getData(playerSender).hasItem(ShopItem.CLOWN_FISH) && (!plugin.loginProcess.CLOWNFISH_TOGGLE.contains(playerSender.getName()))) {
            playerSender.getInventory().addItem(plugin.shop.getClownFish());
            msg("You have been given a Clown Fish", ChatColor.GREEN);
        } else {
            msg("You do not own a Clown Fish or an admin has toggled your ability to use it. Purchase one from the shop.", ChatColor.RED);
        }
        return true;
    }
}
