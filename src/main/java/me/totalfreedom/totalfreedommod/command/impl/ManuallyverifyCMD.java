/*
package me.totalfreedom.totalfreedommod.command;

import me.totalfreedom.totalfreedommod.config.ConfigEntry;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
TODO: Merge with VerifyCMD
@CommandPermissions(level = Rank.ADMIN, source = SourceType.BOTH)
@CommandParameters(name = "manuallyverify", description = "Manually verify someone", usage = "/<command> <playername>", aliases = "mv")
public class ManuallyverifyCMD extends FreedomCommand {
    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        boolean verificationEnabled = ConfigEntry.DISCORD_VERIFICATION.getBoolean();
        if (!plugin.discord.enabled) {
            msg("The Discord verification system is currently disabled.", ChatColor.RED);
            return true;
        }

        if (!verificationEnabled) {
            msg("The Discord verification system is currently disabled.", ChatColor.RED);
            return true;
        }

        if (args.length == 0) {
            return false;
        }

        final Player player = getPlayer(args[0]);
        if (player == null) {
            msg(FreedomCommand.PLAYER_NOT_FOUND);
            return true;
        }

        if (!plugin.playerList.isImpostor(player)) {
            msg("That player is not an impostor.");
            return true;
        }

        FUtil.adminAction(sender.getName(), "Manually verifying player " + player.getName(), false);
        player.setOp(true);
        msg(player, YOU_ARE_OP);

        if (plugin.playerList.getPlayer(player).getFreezeData().isFrozen()) {
            plugin.playerList.getPlayer(player).getFreezeData().setFrozen(false);
            msg(player, "You have been unfrozen.");
        }

        plugin.playerList.verify(player, null);
        plugin.rankManager.updateDisplay(player);
        return true;
    }
}*/
