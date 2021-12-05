package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.BOTH)
@CommandParameters(name = "attributelist", description = "Lists all possible attributes.", usage = "/<command>", aliases = "attrlist")
public class AttributeListCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        StringBuilder list = new StringBuilder("All possible attributes: ");

        for (Attribute attribute : Attribute.values()) {
            list.append(attribute.name()).append(", ");
        }

        // Remove extra comma at the end of the list
        list = new StringBuilder(list.substring(0, list.length() - 2));

        msg(list.toString());
        return true;
    }
}
