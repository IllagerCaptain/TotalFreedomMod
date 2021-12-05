package me.totalfreedom.totalfreedommod.command.impl;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.EnumUtils;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "modifyitem", description = "Modify the current item you are holding.", usage = "/<command> <name <message> | lore <message> | enchant <enchantment> <level> | potion <effect> <duration> <amplifier> | attribute <name> <amount> | clear>", aliases = "mi")
public class ModifyItemCMD extends FreedomCommand {

    @SuppressWarnings("deprecation")
    @Override
    public boolean run(final CommandSender sender, final Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        if (args.length < 1) {
            return false;
        }

        ItemStack item = playerSender.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.AIR)) {
            msg("You must have an item in your hand!");
            return true;
        }

        if (args[0].equalsIgnoreCase("clear")) {
            item.setItemMeta(null);
            playerSender.getInventory().setItemInMainHand(item);
            return true;
        }

        if (args.length < 2) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        switch (args[0]) {
            case "name":
                String name = FUtil.colorize(StringUtils.join(args, " ", 1, args.length));
                meta.setDisplayName(name);
                item.setItemMeta(meta);
                break;

            case "lore":
                List<String> lore = new ArrayList<>();
                for (String line : StringUtils.join(args, " ", 1, args.length).split("\\\\n")) {
                    lore.add(FUtil.colorize(line));
                }
                meta.setLore(lore);
                item.setItemMeta(meta);
                break;

            case "enchant":
                if (args.length < 3) {
                    return false;
                }

                Enchantment enchantment = Enchantment.getByName(args[1].toUpperCase());
                if (enchantment == null) {
                    msg("Invalid enchantment. Please run /enchant list for a list of valid enchantments.");
                    return true;
                }

                int level;
                try {
                    level = Integer.parseInt(args[2]);
                } catch (NumberFormatException ex) {
                    msg("The level specified is not a valid integer.");
                    return true;
                }
                meta.addEnchant(enchantment, level, true);
                item.setItemMeta(meta);
                break;

            case "potion": {
                if (!item.getType().equals(Material.POTION) & !item.getType().equals(Material.SPLASH_POTION) & !item.getType().equals(Material.LINGERING_POTION) & !item.getType().equals(Material.TIPPED_ARROW)) {
                    msg("This item can not have potion effects added to it.");
                    return true;
                }

                if (args.length < 4) {
                    return false;
                }

                PotionEffectType type = PotionEffectType.getByName(args[1]);
                if (type == null) {
                    msg("Invalid potion effect. Please run /potion list for a list of valid potion effects.");
                    return true;
                }

                int duration;
                try {
                    duration = Math.max(1, Math.min(1000000, Integer.parseInt(args[2])));
                } catch (NumberFormatException ex) {
                    msg("The duration specified is not a valid integer.");
                    return true;
                }

                int amplifier;
                try {
                    amplifier = Math.max(1, Math.min(256, Integer.parseInt(args[2])));
                } catch (NumberFormatException ex) {
                    msg("The amplifier specified is not a valid integer.");
                    return true;
                }
                PotionMeta potionMeta = (PotionMeta) meta;
                potionMeta.addCustomEffect(type.createEffect(duration, amplifier), true);
                item.setItemMeta(potionMeta);
                break;
            }

            case "attribute":
                if (args.length < 3) {
                    return false;
                }

                if (!EnumUtils.isValidEnum(Attribute.class, args[1].toUpperCase(Locale.ROOT))) {
                    msg("Invalid attribute. Please run /attributelist for a list of valid attributes.");
                    return true;
                }
                Attribute attribute = Attribute.valueOf(args[1].toUpperCase());

                double amount;
                try {
                    amount = Double.parseDouble(args[2]);
                } catch (NumberFormatException ex) {
                    msg("The amount specified is not a valid integer.");
                    return true;
                }
                if (Double.isNaN(amount)) {
                    msg("The amount specified is illegal.");
                    return true;
                }

                meta.addAttributeModifier(attribute, new AttributeModifier(attribute.getKey().getKey(), amount, AttributeModifier.Operation.ADD_NUMBER));
                item.setItemMeta(meta);
                break;
            default:
                return false;
        }
        playerSender.getInventory().setItemInMainHand(item);
        return true;
    }

    private Multimap<Attribute, AttributeModifier> getAttributeList(ItemStack item) {
        if (item.getItemMeta() == null) return ArrayListMultimap.create();
        return item.getItemMeta().getAttributeModifiers();
    }
}
