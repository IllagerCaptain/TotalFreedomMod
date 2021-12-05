package me.totalfreedom.totalfreedommod.command;

import com.google.common.collect.Lists;
import me.totalfreedom.totalfreedommod.TotalFreedomMod;
import me.totalfreedom.totalfreedommod.admin.Admin;
import me.totalfreedom.totalfreedommod.command.exception.CommandFailException;
import me.totalfreedom.totalfreedommod.command.input.SourceType;
import me.totalfreedom.totalfreedommod.command.input.CommandParameters;
import me.totalfreedom.totalfreedommod.command.input.CommandPermissions;
import me.totalfreedom.totalfreedommod.player.PlayerData;
import me.totalfreedom.totalfreedommod.rank.Rank;
import me.totalfreedom.totalfreedommod.util.FUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.*;

public abstract class FreedomCommand implements CommandExecutor, TabCompleter {
    public static final String YOU_ARE_OP = ChatColor.YELLOW + "You are now op!";
    public static final String YOU_ARE_NOT_OP = ChatColor.YELLOW + "You are no longer op!";
    public static final String PLAYER_NOT_FOUND = ChatColor.GRAY + "Player not found!";
    public static final String ONLY_CONSOLE = ChatColor.RED + "Only console senders may execute this command!";
    public static final String ONLY_IN_GAME = ChatColor.RED + "Only in-game players may execute this command!";
    public static final String NO_PERMISSION = ChatColor.RED + "You do not have permission to execute this command.";
    public static final Timer timer = new Timer();
    public static final Map<CommandSender, FreedomCommand> COOLDOWN_TIMERS = new HashMap<>();
    private static CommandMap commandMap;
    protected final TotalFreedomMod plugin = TotalFreedomMod.getPlugin();
    protected final Server server = plugin.getServer();
    private final String name;
    private final String description;
    private final String usage;
    private final String aliases;
    private final Rank level;
    private final SourceType source;
    private final boolean blockHostConsole;
    private final int cooldown;
    private final CommandParameters params;
    private final CommandPermissions perms;
    protected CommandSender sender;

    public FreedomCommand() {
        this.params = getClass().getAnnotation(CommandParameters.class);
        this.perms = getClass().getAnnotation(CommandPermissions.class);
        this.name = params.name();
        this.description = params.description();
        this.usage = params.usage();
        this.aliases = params.aliases();
        this.level = perms.level();
        this.source = perms.source();
        this.blockHostConsole = perms.blockHostConsole();
        this.cooldown = perms.cooldown();
    }

    public static CommandMap getCommandMap() {
        if (commandMap == null) {
            try {
                final Field f = Bukkit.getServer().getPluginManager().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap) f.get(Bukkit.getServer().getPluginManager());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return commandMap;
    }

    public static FreedomCommand getFrom(Command command) {
        try {
            return (FreedomCommand) (((PluginCommand) command).getExecutor());
        } catch (Exception ex) {
            return null;
        }
    }

    public void register() {
        FCommand cmd = new FCommand(this.name);
        if (this.aliases != null) {
            cmd.setAliases(Arrays.asList(StringUtils.split(this.aliases, ",")));
        }
        if (this.description != null) {
            cmd.setDescription(this.description);
        }
        if (this.usage != null) {
            cmd.setUsage(this.usage);
        }
        getCommandMap().register("totalfreedommod", cmd);
        cmd.setExecutor(this);
    }

    protected void msg(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.GRAY + message);
    }

    protected void msg(Player player, String message) {
        player.sendMessage(ChatColor.GRAY + message);
    }

    protected void msg(Player player, String message, ChatColor color) {
        player.sendMessage(color + message);
    }

    protected void msg(String message) {
        msg(sender, message);
    }

    protected void msg(String message, ChatColor color) {
        msg(color + message);
    }

    protected void msg(String message, net.md_5.bungee.api.ChatColor color) {
        msg(color + message);
    }

    protected boolean isAdmin(Player player) {
        return plugin.adminList.isAdmin(player);
    }

    protected boolean isAdmin(CommandSender sender) {
        return plugin.adminList.isAdmin(sender);
    }

    protected void checkConsole() {
        if (!isConsole()) {
            throw new CommandFailException(ONLY_CONSOLE);
        }
    }

    protected void checkPlayer() {
        if (isConsole()) {
            throw new CommandFailException(ONLY_IN_GAME);
        }
    }

    protected void checkRank(Rank rank) {
        if (!plugin.rankManager.getRank(sender).isAtLeast(rank)) {
            noPerms();
        }
    }

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String commandLabel, @NotNull String[] args) {
        try {
            boolean run = run(sender, sender instanceof ConsoleCommandSender ? null : (Player) sender, cmd, commandLabel, args, sender instanceof ConsoleCommandSender);
            if (!run) {
                msg(ChatColor.WHITE + cmd.getUsage().replace("<command>", cmd.getLabel()));
                return true;
            }
        } catch (CommandFailException ex) {
            msg(ChatColor.RED + ex.getMessage());
        }
        return false;
    }

    @NotNull
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        List<String> options = getTabCompleteOptions(sender, command, alias, args);
        if (options == null) {
            return new ArrayList<>();
        }
        return StringUtil.copyPartialMatches(args[args.length - 1], options, Lists.newArrayList());
    }

    public abstract boolean run(CommandSender sender, Player playerSender, Command cmd, String commandLabel, String[] args, boolean senderIsConsole);

    protected List<String> getTabCompleteOptions(CommandSender sender, Command command, String alias, String[] args) {
        return FUtil.getPlayerList();
    }

    protected boolean isConsole() {
        return sender instanceof ConsoleCommandSender;
    }

    protected Player getPlayer(String name) {
        return Bukkit.getPlayer(name);
    }

    protected Player getPlayer(String name, Boolean nullVanished) {
        Player player = Bukkit.getPlayer(name);
        if (player != null) {
            if (nullVanished && plugin.adminList.isVanished(player.getName()) && !plugin.adminList.isAdmin(sender)) {
                return null;
            }
        }
        return player;
    }

    protected Admin getAdmin(CommandSender sender) {
        return plugin.adminList.getAdmin(sender);
    }

    protected Admin getAdmin(Player player) {
        return plugin.adminList.getAdmin(player);
    }

    protected PlayerData getData(Player player) {
        return plugin.playerList.getData(player);
    }

    protected boolean noPerms() {
        throw new CommandFailException(NO_PERMISSION);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public String getAliases() {
        return aliases;
    }

    public Rank getLevel() {
        return level;
    }

    public SourceType getSource() {
        return source;
    }

    public boolean isBlockHostConsole() {
        return blockHostConsole;
    }

    public int getCooldown() {
        return cooldown;
    }

    public CommandParameters getParams() {
        return params;
    }

    public CommandPermissions getPerms() {
        return perms;
    }

    private final class FCommand extends Command {
        private FreedomCommand cmd = null;

        private FCommand(String command) {
            super(command);
        }

        public void setExecutor(FreedomCommand cmd) {
            this.cmd = cmd;
        }

        public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, String[] args) {
            if (cmd != null) {
                cmd.sender = sender;

                if (func4()) {
                    return true;
                }

                if (func1()) {
                    return true;
                }

                if (func2()) {
                    return true;
                }

                func3();

                return cmd.onCommand(sender, this, commandLabel, args);
            }
            return false;
        }

        public boolean func1() {
            if (perms.source() == SourceType.ONLY_CONSOLE && sender instanceof Player) {
                msg(ONLY_CONSOLE);
                return true;
            }

            if (perms.source() == SourceType.ONLY_IN_GAME && sender instanceof ConsoleCommandSender) {
                msg(ONLY_IN_GAME);
                return true;
            }

            return false;
        }

        public boolean func2() {
            if (!plugin.rankManager.getRank(sender).isAtLeast(perms.level())) {
                msg(NO_PERMISSION);
                return true;
            }

            if (perms.blockHostConsole() && FUtil.isFromHostConsole(sender.getName()) && !FUtil.inDeveloperMode()) {
                msg(ChatColor.RED + "Host console is not allowed to use this command!");
                return true;
            }
            return false;
        }

        public void func3() {
            if (perms.cooldown() != 0 && !isAdmin(sender)) {
                COOLDOWN_TIMERS.put(sender, cmd);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        COOLDOWN_TIMERS.remove(sender);
                    }
                }, perms.cooldown() * 1000L);
            }
        }

        public boolean func4() {
            if (COOLDOWN_TIMERS.containsKey(sender) && COOLDOWN_TIMERS.containsValue(cmd)) {
                msg(ChatColor.RED + "You are on cooldown for this command.");
                return true;
            }
            return false;
        }

        @NotNull
        @Override
        public List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, String[] args) {
            if (cmd != null) {
                return cmd.onTabComplete(sender, this, alias, args);
            }
            return new ArrayList<>();
        }
    }
}