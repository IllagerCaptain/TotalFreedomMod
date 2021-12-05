package me.totalfreedom.totalfreedommod.command.impl;

import me.totalfreedom.totalfreedommod.admin.ActivityLogEntry;
import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@CommandPermissions(level = Rank.ADMIN, source = SourceType.ONLY_IN_GAME)
@CommandParameters(name = "playtime", description = "Gets your playtime statistics.", usage = "/<command>")
public class PlayTimeCMD extends FreedomCommand {
    @Override
    public boolean run(final CommandSender sender, final Player playerSender, final Command cmd, final String commandLabel, final String[] args, final boolean senderIsConsole) {
        ActivityLogEntry entry = plugin.activityLog.getActivityLog(playerSender);
        int seconds = entry.getTotalSecondsPlayed();
        int minutes = 0;
        int hours = 0;
        while (seconds >= 60) {
            seconds -= 60;
            minutes += 1;
        }
        while (minutes >= 60) {
            minutes -= 60;
            hours += 1;
        }
        if (entry.getTimestamps().size() == 0) {
            entry.addLogin();
        }
        String lastLoginString = entry.getTimestamps().get(entry.getTimestamps().size() - 1);
        Date currentTime = Date.from(Instant.now());
        lastLoginString = lastLoginString.replace("Login: ", "");
        Date lastLogin = FUtil.stringToDate(lastLoginString);

        long duration = currentTime.getTime() - lastLogin.getTime();
        long cseconds = duration / 1000 % 60;
        long cminutes = duration / (60 * 1000) % 60;
        long chours = duration / (60 * 60 * 1000);
        StringBuilder sb = new StringBuilder()
                .append("Playtime - ")
                .append(sender.getName())
                .append("\n")
                .append("Current Session: ")
                .append(chours)
                .append(" hours, ")
                .append(cminutes)
                .append(" minutes, and ")
                .append(cseconds)
                .append(" seconds")
                .append("\n")
                .append("Overall: ")
                .append(hours)
                .append(" hours, ")
                .append(minutes)
                .append(" minutes, and ")
                .append(seconds)
                .append(" seconds")
                .append("\n");
        List<String> durations = entry.getDurations();
        if (durations.size() >= 3) {
            sb.append("Recent Sessions:");
            for (int i = 0; i < 3; i++) {
                sb.append("\n" + " - ").append(durations.get((durations.size() - 1) - i));
            }
        }
        msg(sb.toString());
        return true;
    }
}
