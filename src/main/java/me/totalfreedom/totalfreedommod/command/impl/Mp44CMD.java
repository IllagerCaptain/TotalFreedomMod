package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.player.FPlayer;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "mp44", description = "Modern weaponry, FTW. Use 'draw' to start firing, 'sling' to stop firing.", usage = "/<command> <draw | sling>")
public class Mp44CMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (!ConfigEntry.MP44_ENABLED.getBoolean()) {
            msg("The mp44 is currently disabled.", ChatColor.GREEN);
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        FPlayer playerdata = plugin.playerList.getPlayer(playerSender);

        if (args[0].equalsIgnoreCase("draw")) {
            playerdata.armMP44();

            msg("mp44 is ARMED! Left click with gunpowder to start firing, left click again to quit.", ChatColor.GREEN);
            msg("Type /mp44 sling to disable.  -by Madgeek1450", ChatColor.GREEN);

            Objects.requireNonNull(playerSender.getEquipment()).setItemInMainHand(new ItemStack(Material.GUNPOWDER, 1));
        } else {
            playerdata.disarmMP44();

            msg("mp44 Disarmed.", ChatColor.GREEN);
        }

        return true;
    }
}
