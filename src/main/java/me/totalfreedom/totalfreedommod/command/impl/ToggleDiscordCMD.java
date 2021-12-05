package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.player.PlayerData;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "togglediscord", description = "Toggle the display of Discord messages in-game.", usage = "/<command>", aliases = "tdiscord,tdisc")
public class ToggleDiscordCMD extends FreedomCommand {
    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        PlayerData data = plugin.playerList.getData(playerSender);
        data.setDisplayDiscord(!data.doesDisplayDiscord());
        plugin.playerList.save(data);
        msg("Discord messages will " + (data.doesDisplayDiscord() ? "now" : "no longer") + " be shown.");
        return true;
    }
}
