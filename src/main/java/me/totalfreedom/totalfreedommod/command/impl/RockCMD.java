package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "rock", description = "You have thrown a rock, but you have also summoned a meteor!", usage = "/<command>")
public class RockCMD extends FreedomCommand {

    public static final String ROCK_LYRICS = ChatColor.BLUE + "You have thrown a rock, but you have also summoned a meteor!";

    @Override
    public boolean run(final CommandSender sender, final Player playerSender, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) {
        final ItemStack heldItem = new ItemStack(Material.STONE);
        final ItemMeta heldItemMeta = heldItem.getItemMeta();
        assert heldItemMeta != null;
        heldItemMeta.setDisplayName(ChatColor.BLUE + "Rock");
        heldItem.setItemMeta(heldItemMeta);

        for (final Player player : this.server.getOnlinePlayers()) {
            final int firstEmpty = player.getInventory().firstEmpty();
            if (firstEmpty >= 0) {
                player.getInventory().setItem(firstEmpty, heldItem);
            }
        }

        FUtil.bcastMsg(ROCK_LYRICS);
        return true;
    }
}
