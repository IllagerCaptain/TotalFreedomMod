package me.totalfreedom.totalfreedommod.command.impl;

import io.papermc.lib.PaperLib;
import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.command.FreedomCommand;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.world.manager.WorldTime;
import me.totalfreedom.totalfreedommod.world.manager.WorldWeather;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@CommandPermissions(level = Rank.OP, source = SourceType.BOTH)
@CommandParameters(name = "adminworld", description = "Allows for admins to configure time, and weather of the AdminWorld, and allows for admins and ops to go to the AdminWorld.",
        usage = "/<command> [time <morning | noon | evening | night> | weather <off | rain | storm>]",
        aliases = "sw,aw,staffworld")
public class AdminWorldCMD extends FreedomCommand {

    @Override
    public boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole) {
        CommandMode commandMode = null;

        if (args.length == 0) {
            commandMode = CommandMode.TELEPORT;
        } else if (args.length >= 2) {
            if ("time".equalsIgnoreCase(args[0])) {
                commandMode = CommandMode.TIME;
            } else if ("weather".equalsIgnoreCase(args[0])) {
                commandMode = CommandMode.WEATHER;
            }
        }

        if (commandMode == null) {
            return false;
        }

        try {
            switch (commandMode) {
                case TELEPORT: {
                    if (!(sender instanceof Player) || playerSender == null) {
                        return false;
                    }

                    World adminWorld = null;
                    try {
                        adminWorld = plugin.worldManager.adminworld.getWorld();
                    } catch (Exception ignored) {
                    }

                    if (adminWorld == null || playerSender.getWorld() == adminWorld) {
                        msg("Going to the main world.");
                        PaperLib.teleportAsync(playerSender, server.getWorlds().get(0).getSpawnLocation());
                    } else {
                        msg("Going to the AdminWorld.");
                        plugin.worldManager.adminworld.sendToWorld(playerSender);
                    }
                    break;
                }
                case TIME: {
                    assertCommandPerms(sender, playerSender);

                    if (args.length == 2) {
                        WorldTime timeOfDay = WorldTime.getByAlias(args[1]);
                        if (timeOfDay != null) {
                            plugin.worldManager.adminworld.setTimeOfDay(timeOfDay);
                            msg("AdminWorld time set to: " + timeOfDay.name());
                        } else {
                            msg("Invalid time of day. Can be: sunrise, noon, sunset, midnight");
                        }
                    } else {
                        return false;
                    }

                    break;
                }
                case WEATHER: {
                    assertCommandPerms(sender, playerSender);

                    if (args.length == 2) {
                        WorldWeather weatherMode = WorldWeather.getByAlias(args[1]);
                        if (weatherMode != null) {
                            plugin.worldManager.adminworld.setWeatherMode(weatherMode);
                            msg("AdminWorld weather set to: " + weatherMode.name());
                        } else {
                            msg("Invalid weather mode. Can be: off, rain, storm");
                        }
                    } else {
                        return false;
                    }

                    break;
                }
                default: {
                    return false;
                }
            }
        } catch (PermissionDeniedException ex) {
            if (ex.getMessage().isEmpty()) {
                return noPerms();
            }
            msg(ex.getMessage());
            return true;
        }

        return true;
    }

    // TODO: Redo this properly
    private void assertCommandPerms(CommandSender sender, Player playerSender) throws PermissionDeniedException {
        if (!(sender instanceof Player) || playerSender == null || !isAdmin(sender)) {
            throw new PermissionDeniedException();
        }
    }

    @Override
    public List<String> getTabCompleteOptions(CommandSender sender, Command command, String alias, String[] args) {
        if (!plugin.adminList.isAdmin(sender)) {
            return Collections.emptyList();
        }
        if (args.length == 1) {
            return Arrays.asList("time", "weather");
        } else if (args.length == 2) {
            if (args[0].equals("time")) {
                return Arrays.asList("morning", "noon", "evening", "night");
            } else if (args[0].equals("weather")) {
                return Arrays.asList("off", "rain", "storm");
            }
        }
        return Collections.emptyList();
    }

    private enum CommandMode {
        TELEPORT, TIME, WEATHER
    }

    private static class PermissionDeniedException extends Exception {

        private static final long serialVersionUID = 1L;

        private PermissionDeniedException() {
            super("");
        }

        private PermissionDeniedException(String string) {
            super(string);
        }
    }
}