package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@CommandPermissions(level = Rank.OP, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "glow", description = "Toggles the glowing outline effect because y'all lazy as fuck", usage = "/<command>")
public class GlowCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        boolean glowing = false;
        if (playerSender.getPotionEffect(PotionEffectType.GLOWING) != null) {
            playerSender.removePotionEffect(PotionEffectType.GLOWING);
        } else {
            PotionEffect glow = new PotionEffect(PotionEffectType.GLOWING, 1000000, 1, false, false);
            playerSender.addPotionEffect(glow);
            glowing = true;
        }
        msg("You are " + (glowing ? "now" : "no longer") + " glowing.");
        return true;
    }
}
