package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.player.PlayerData;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.shop.ShopItem;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "loginmessage", description = "Change your login message", usage = "/<command> [message]")
public class LoginMessageCMD extends FreedomCommand {
    @Override
    public boolean run(final CommandSender sender, final Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (!plugin.playerList.getData(playerSender).hasItem(ShopItem.LOGIN_MESSAGES) && !isAdmin(playerSender)) {
            msg("You did not purchase the ability to use login messages! Purchase the ability from the shop.", ChatColor.RED);
            return true;
        }

        if (args.length == 0) {
            playerSender.openInventory(plugin.shop.generateLoginMessageGUI(playerSender));
            return true;
        }

        checkRank(Rank.ADMIN);

        String message = StringUtils.join(args, " ");
        if (!message.contains("%rank%") && !message.contains("%coloredrank%")) {
            msg("Your login message must contain your rank. Use either %rank% or %coloredrank% to specify where you want the rank", ChatColor.RED);
            return true;
        }
        int length = message.replace("%name%", "").replace("%rank%", "").replace("%coloredrank%", "").replace("%art%", "").length();
        if (length > 100) {
            msg("Your login message cannot be more than 100 characters (excluding your rank and your name)", ChatColor.RED);
            return true;
        }
        PlayerData data = getData(playerSender);
        data.setLoginMessage(message);
        plugin.playerList.save(data);
        msg("Your login message is now the following:\n" + plugin.rankManager.craftLoginMessage(playerSender, message), ChatColor.GREEN);
        return true;
    }
}
