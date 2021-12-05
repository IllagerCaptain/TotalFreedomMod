package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@CommandPermissions(level = Rank.OP, source = SourceType.BOTH)
@CommandParameters(name = "masterbuilderinfo", description = "Information on how to apply for Master Builder.", usage = "/<command>", aliases = "mbi,builderinfo,mbinfo")
public class BuilderInfoCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        List<String> masterBuilderInfo = ConfigEntry.MASTER_BUILDER_INFO.getStringList();

        if (masterBuilderInfo.isEmpty()) {
            msg("The master builder information section of the config.yml file has not been configured.", ChatColor.RED);
        } else {
            msg(FUtil.colorize(StringUtils.join(masterBuilderInfo, "\n")));
        }

        return true;
    }
}
