package me.totalfreedom.totalfreedommod.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import net.minecraft.server.v1_16_R2.NBTTagCompound;
import net.minecraft.server.v1_16_R2.NBTTagList;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(description = "Modify the current item you are holding.", usage = "/<command> <name <message> | lore <message> | enchant <enchantment> <level> | potion <effect> <duration> <amplifier> | attribute <name> <amount> | clear>", aliases = "mi")
public class Command_modifyitem extends FreedomCommand
{

    @Override
    public boolean run(final CommandSender sender, final Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole)
    {
        if (args.length < 1)
        {
            return false;
        }

        ItemStack item = playerSender.getInventory().getItemInMainHand();
        if (item.getType().equals(Material.AIR))
        {
            msg("You must have an item in your hand!");
            return true;
        }

        if (args[0].equalsIgnoreCase("clear"))
        {
            item.setItemMeta(null);
            playerSender.getInventory().setItemInMainHand(item);
            return true;
        }

        if (args.length < 2)
        {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        switch (args[0])
        {
            case "name":
                String name = FUtil.colorize(StringUtils.join(args, " ", 1, args.length));
                meta.setDisplayName(name);
                item.setItemMeta(meta);
                break;

            case "lore":
                List<String> lore = new ArrayList<>();
                for (String line : StringUtils.join(args, " ", 1, args.length).split("\\\\n"))
                {
                    lore.add(FUtil.colorize(line));
                }
                meta.setLore(lore);
                item.setItemMeta(meta);
                break;

            case "enchant":
                if (args.length < 3)
                {
                    return false;
                }

                Enchantment enchantment = Enchantment.getByName(args[1].toUpperCase());
                if (enchantment == null)
                {
                    msg("Invalid enchantment. Please run /enchantments for a list of valid enchantments.");
                    return true;
                }

                int level;
                try
                {
                    level = Integer.parseInt(args[2]);
                }
                catch (NumberFormatException ex)
                {
                    msg("The level specified is not a valid integer.");
                    return true;
                }
                meta.addEnchant(enchantment, level, true);
                item.setItemMeta(meta);
                break;

            case "potion":
            {
                if (!item.getType().equals(Material.POTION) & !item.getType().equals(Material.SPLASH_POTION) & !item.getType().equals(Material.LINGERING_POTION) & !item.getType().equals(Material.TIPPED_ARROW))
                {
                    msg("This item can not have potion effects added to it.");
                    return true;
                }

                if (args.length < 4)
                {
                    return false;
                }

                PotionEffectType type = PotionEffectType.getByName(args[1]);
                if (type == null)
                {
                    msg("Invalid potion effect. Please run /potion list for a list of valid potion effects.");
                    return true;
                }

                int duration;
                try
                {
                    duration = Math.max(1, Math.min(1000000, Integer.parseInt(args[2])));
                }
                catch (NumberFormatException ex)
                {
                    msg("The duration specified is not a valid integer.");
                    return true;
                }

                int amplifier;
                try
                {
                    amplifier = Math.max(1, Math.min(256, Integer.parseInt(args[2])));
                }
                catch (NumberFormatException ex)
                {
                    msg("The amplifier specified is not a valid integer.");
                    return true;
                }
                PotionMeta potionMeta = (PotionMeta)meta;
                potionMeta.addCustomEffect(type.createEffect(duration, amplifier), true);
                item.setItemMeta(potionMeta);
                break;
            }

            case "attribute":
                if (args.length < 3)
                {
                    return false;
                }
                net.minecraft.server.v1_16_R2.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
                NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
                NBTTagList modifiers = getAttributeList(nmsStack);
                NBTTagCompound cmpnd = new NBTTagCompound();
                Attribute attribute = Attribute.getByName(args[1].toUpperCase());
                if (attribute == null)
                {
                    msg("Invalid attribute. Please run /attributelist for a list of valid attributes.");
                    return true;
                }
                cmpnd.setString("AttributeName", attribute.getAttribute());
                cmpnd.setString("Name", attribute.getAttribute());
                double amount;
                try
                {
                    amount = Double.parseDouble(args[2]);
                }
                catch (NumberFormatException ex)
                {
                    msg("The amount specified is not a valid integer.");
                    return true;
                }
                if (Double.isNaN(amount))
                {
                    msg("The amount specified is illegal.");
                    return true;
                }
                cmpnd.setDouble("Amount", amount);
                cmpnd.setInt("Operation", 0);
                Random random = new Random();
                cmpnd.setIntArray("UUID", new int[]
                        {
                                random.nextInt(),
                                random.nextInt(),
                                random.nextInt(),
                                random.nextInt()
                        });
                cmpnd.setString("Slot", "mainhand");
                modifiers.add(cmpnd);
                compound.set("AttributeModifiers", modifiers);
                nmsStack.setTag(compound);
                item = CraftItemStack.asBukkitCopy(nmsStack);
                break;
            default:
                return false;
        }
        playerSender.getInventory().setItemInMainHand(item);
        return true;
    }

    private NBTTagList getAttributeList(net.minecraft.server.v1_16_R2.ItemStack stack)
    {
        if (stack.getTag() == null)
        {
            stack.setTag(new NBTTagCompound());
        }
        NBTTagList attr = stack.getTag().getList("AttributeModifiers", 10);
        if (attr == null)
        {
            stack.getTag().set("AttributeModifiers", new NBTTagList());
        }
        return stack.getTag().getList("AttributeModifiers", 10);
    }

    private enum Attribute
    {
        GENERIC_MAX_HEALTH("GENERIC_MAX_HEALTH", "generic.max_health"),
        GENERIC_FOLLOW_RANGE("GENERIC_FOLLOW_RANGE", "generic.follow_range"),
        GENERIC_KNOCKBACK_RESISTANCE("GENERIC_KNOCKBACK_RESISTANCE", "generic.knockback_resistance"),
        GENERIC_MOVEMENT_SPEED("GENERIC_MOVEMENT_SPEED", "generic.movement_speed"),
        GENERIC_FLYING_SPEED("GENERIC_FLYING_SPEED", "generic.flying_speed"),
        GENERIC_ATTACK_DAMAGE("GENERIC_ATTACK_DAMAGE", "generic.attack_damage"),
        GENERIC_ATTACK_SPEED("GENERIC_ATTACK_SPEED", "generic.attack_speed"),
        GENERIC_ARMOR("GENERIC_ARMOR", "generic.armor"),
        GENERIC_ARMOR_TOUGHNESS("GENERIC_ARMOR_TOUGHNESS", "generic.armor_toughmess"),
        GENERIC_LUCK("GENERIC_LUCK", "generic.luck"),
        HORSE_JUMP_STRENGTH("GENERIC_MAX_HEALTH", "horse.jump_strength"),
        ZOMBIE_SPAWN_REINFORCEMENTS("ZOMBIE_SPAWN_REINFORCEMENTS", "zombie.spawn_reinforcements");

        private final String name;
        private final String attribute;

        Attribute(String name, String attribute)
        {
            this.name = name;
            this.attribute = attribute;
        }

        public String getAttribute()
        {
            return attribute;
        }

        @Override
        public String toString()
        {
            return name;
        }

        public static Attribute getByName(String name)
        {
            for (Attribute attr : Attribute.values())
            {
                if (attr.toString().toUpperCase().equals(name))
                {
                    return attr;
                }
            }
            return null;
        }
    }
}
