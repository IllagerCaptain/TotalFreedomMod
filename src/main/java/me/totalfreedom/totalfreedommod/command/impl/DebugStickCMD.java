package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "debugstick", description = "Get a stick of happiness.", usage = "/<command>")
public class DebugStickCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        ItemStack itemStack = new ItemStack(Material.DEBUG_STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Stick of Happiness");
        List<String> lore = Arrays.asList(
                ChatColor.RED + "This is the most powerful stick in the game.",
                ChatColor.DARK_BLUE + "You can left click to select what you want to change.",
                ChatColor.DARK_GREEN + "And then you can right click to change it!",
                ChatColor.DARK_PURPLE + "Isn't technology amazing?");
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        playerSender.getInventory().addItem(itemStack);
        return true;
    }
}
