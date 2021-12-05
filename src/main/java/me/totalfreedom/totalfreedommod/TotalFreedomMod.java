package me.totalfreedom.totalfreedommod;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import me.totalfreedom.totalfreedommod.admin.ActivityLog;
import me.totalfreedom.totalfreedommod.admin.AdminList;
import me.totalfreedom.totalfreedommod.admin.module.CommandSpy;
import me.totalfreedom.totalfreedommod.admin.module.Fuckoff;
import me.totalfreedom.totalfreedommod.admin.module.Muter;
import me.totalfreedom.totalfreedommod.admin.module.Orbiter;
import me.totalfreedom.totalfreedommod.anticheat.AutoEject;
import me.totalfreedom.totalfreedommod.punishments.banning.BanManager;
import me.totalfreedom.totalfreedommod.punishments.banning.IndefiniteBanList;
import me.totalfreedom.totalfreedommod.blocking.BlockBlocker;
import me.totalfreedom.totalfreedommod.blocking.EditBlocker;
import me.totalfreedom.totalfreedommod.blocking.EventBlocker;
import me.totalfreedom.totalfreedommod.blocking.InteractBlocker;
import me.totalfreedom.totalfreedommod.blocking.MobBlocker;
import me.totalfreedom.totalfreedommod.blocking.PVPBlocker;
import me.totalfreedom.totalfreedommod.blocking.PotionBlocker;
import me.totalfreedom.totalfreedommod.blocking.SignBlocker;
import me.totalfreedom.totalfreedommod.blocking.command.CommandBlocker;
import me.totalfreedom.totalfreedommod.bridge.BukkitTelnetBridge;
import me.totalfreedom.totalfreedommod.bridge.CoreProtectBridge;
import me.totalfreedom.totalfreedommod.bridge.EssentialsBridge;
import me.totalfreedom.totalfreedommod.bridge.LibsDisguisesBridge;
import me.totalfreedom.totalfreedommod.bridge.TFGuildsBridge;
import me.totalfreedom.totalfreedommod.bridge.WorldEditBridge;
import me.totalfreedom.totalfreedommod.bridge.WorldGuardBridge;
import me.totalfreedom.totalfreedommod.punishments.caging.Cager;
import me.totalfreedom.totalfreedommod.command.CommandLoader;
import me.totalfreedom.totalfreedommod.config.MainConfig;
import me.totalfreedom.totalfreedommod.discord.Discord;
import me.totalfreedom.totalfreedommod.punishments.freeze.Freezer;
import me.totalfreedom.totalfreedommod.fun.ItemFun;
import me.totalfreedom.totalfreedommod.fun.Jumppads;
import me.totalfreedom.totalfreedommod.fun.Landminer;
import me.totalfreedom.totalfreedommod.fun.MP44;
import me.totalfreedom.totalfreedommod.fun.Trailer;
import me.totalfreedom.totalfreedommod.httpd.HTTPDaemon;
import me.totalfreedom.totalfreedommod.anticheat.AntiNuke;
import me.totalfreedom.totalfreedommod.anticheat.AntiSpam;
import me.totalfreedom.totalfreedommod.anticheat.AutoKick;
import me.totalfreedom.totalfreedommod.player.permissions.PermissionConfig;
import me.totalfreedom.totalfreedommod.player.permissions.PermissionManager;
import me.totalfreedom.totalfreedommod.player.PlayerList;
import me.totalfreedom.totalfreedommod.punishments.PunishmentList;
import me.totalfreedom.totalfreedommod.rank.RankManager;
import me.totalfreedom.totalfreedommod.services.ServiceHandler;
import me.totalfreedom.totalfreedommod.services.impl.*;
import me.totalfreedom.totalfreedommod.shop.Shop;
import me.totalfreedom.totalfreedommod.shop.Votifier;
import me.totalfreedom.totalfreedommod.sql.SQLite;
import me.totalfreedom.totalfreedommod.util.FLog;
import me.totalfreedom.totalfreedommod.util.FUtil;
import me.totalfreedom.totalfreedommod.util.MethodTimer;
import me.totalfreedom.totalfreedommod.world.generator.CleanroomChunkGenerator;
import me.totalfreedom.totalfreedommod.world.WorldManager;
import me.totalfreedom.totalfreedommod.world.WorldRestrictions;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class TotalFreedomMod extends JavaPlugin
{
    public static final String CONFIG_FILENAME = "config.yml";
    //
    public static final BuildProperties build = new BuildProperties();
    //
    public static String pluginName;
    public static String pluginVersion;
    private static TotalFreedomMod plugin;

    private File spigotFile;
    private YamlConfiguration spigotConfig;

    //
    public MainConfig config;
    public PermissionConfig permissions;
    //
    // Service Handler
    public ServiceHandler serviceHandler;
    // Command Loader
    public CommandLoader commandLoader;
    // Services
    public ServerInterface serverInterface;
    public WorldManager worldManager;
    public LogViewer logViewer;
    public AdminList adminList;
    public ActivityLog activityLog;
    public RankManager rankManager;
    public CommandBlocker commandBlocker;
    public EventBlocker eventBlocker;
    public BlockBlocker blockBlocker;
    public MobBlocker mobBlocker;
    public InteractBlocker interactBlocker;
    public PotionBlocker potionBlocker;
    public LoginProcess loginProcess;
    public AntiNuke antiNuke;
    public AntiSpam antiSpam;
    public PlayerList playerList;
    public Shop shop;
    public Votifier votifier;
    public SQLite sql;
    public Announcer announcer;
    public ChatHandler chatManager;
    public Discord discord;
    public PunishmentList punishmentList;
    public BanManager banManager;
    public IndefiniteBanList indefiniteBanList;
    public PermissionManager permissionManager;
    public GameRuleHandler gameRuleHandler;
    public CommandSpy commandSpy;
    public Cager cager;
    public Freezer freezer;
    public EditBlocker editBlocker;
    public PVPBlocker pvpBlocker;
    public Orbiter orbiter;
    public Muter muter;
    public Fuckoff fuckoff;
    public AutoKick autoKick;
    public AutoEject autoEject;
    public Monitors monitors;
    public MovementValidator movementValidator;
    public ServerPing serverPing;
    public ItemFun itemFun;
    public Landminer landMiner;
    public MP44 mp44;
    public Jumppads jumpPads;
    public Trailer trailer;
    public HTTPDaemon httpDaemon;
    public WorldRestrictions worldRestrictions;
    public SignBlocker signBlocker;
    public EntityWiper entityWiper;
    public Sitter sitter;
    public VanishHandler vanishHandler;
    public Pterodactyl pterodactyl;
    //
    // Bridges
    public BukkitTelnetBridge bukkitTelnetBridge;
    public EssentialsBridge essentialsBridge;
    public LibsDisguisesBridge libsDisguisesBridge;
    public CoreProtectBridge coreProtectBridge;
    public TFGuildsBridge tfGuildsBridge;
    public WorldEditBridge worldEditBridge;
    public WorldGuardBridge worldGuardBridge;

    public static TotalFreedomMod getPlugin()
    {
        return plugin;
    }

    public static TotalFreedomMod plugin()
    {
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
        {
            if (plugin.getName().equalsIgnoreCase(pluginName))
            {
                return (TotalFreedomMod)plugin;
            }
        }
        return null;
    }

    @Override
    public void onLoad()
    {
        plugin = this;
        TotalFreedomMod.pluginName = plugin.getDescription().getName();
        TotalFreedomMod.pluginVersion = plugin.getDescription().getVersion();

        FLog.setPluginLogger(plugin.getLogger());
        FLog.setServerLogger(getServer().getLogger());

        build.load(plugin);
    }

    @Override
    public void onEnable()
    {
        FLog.info("Created by Madgeek1450 and Prozza");
        FLog.info("Version " + build.version);
        FLog.info("Compiled " + build.date + " by " + build.author);

        final MethodTimer timer = new MethodTimer();
        timer.start();

        // Warn if we're running on a wrong version
        //ServerInterface.warnVersion();no more nms

        // Delete unused files
        FUtil.deleteCoreDumps();
        FUtil.deleteFolder(new File("./_deleteme"));

        serviceHandler = new ServiceHandler();

        config = new MainConfig();
        config.load();

        if (FUtil.inDeveloperMode())
        {
            FLog.debug("Developer mode enabled.");
        }

        commandLoader = new CommandLoader();
        commandLoader.loadCommands();

        BackupHandler backups = new BackupHandler();
        backups.createAllBackups();

        permissions = new PermissionConfig();
        permissions.load();

        movementValidator = new MovementValidator();
        serverPing = new ServerPing();

        new Initializer();

        serviceHandler.startServices();

        FLog.info("Started " + serviceHandler.getServiceAmount() + " services.");

        timer.update();
        FLog.info("Version " + pluginVersion + " for " + getServer().getBukkitVersion() + " enabled in " + timer.getTotal() + "ms");

        // Metrics @ https://bstats.org/plugin/bukkit/TotalFreedomMod/2966
        new Metrics(this, 2966);

        // little workaround to stop spigot from autorestarting - causing AMP to detach from process.
        //SpigotConfig.config.set("settings.restart-on-crash", false);


        this.spigotFile = new File(getServer().getWorldContainer(), "spigot.yml");
        this.spigotConfig = YamlConfiguration.loadConfiguration(this.spigotFile);

        this.spigotConfig.set("settings.restart-on-crash", false);
        try {
            this.spigotConfig.save(this.spigotFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Possibly could make it so after reloads, players regain their tag
        Bukkit.getOnlinePlayers().forEach(player -> this.rankManager.updateDisplay(player));
    }

    @Override
    public void onDisable()
    {
        // Stop services and bridges
        serviceHandler.stopServices();

        getServer().getScheduler().cancelTasks(plugin);

        FLog.info("Plugin disabled");
    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, String id)
    {
        return new CleanroomChunkGenerator(id);
    }

    public static class BuildProperties
    {
        public String author;
        public String codename;
        public String version;
        public String number;
        public String date;
        public String head;

        public void load(TotalFreedomMod plugin)
        {
            try
            {
                final Properties props;

                try (InputStream in = plugin.getResource("build.properties"))
                {
                    props = new Properties();
                    props.load(in);
                }

                author = props.getProperty("buildAuthor", "unknown");
                codename = props.getProperty("buildCodeName", "unknown");
                version = props.getProperty("buildVersion", pluginVersion);
                number = props.getProperty("buildNumber", "1");
                date = props.getProperty("buildDate", "unknown");
                // Need to do this or it will display ${git.commit.id.abbrev}
                head = props.getProperty("buildHead", "unknown").replace("${git.commit.id.abbrev}", "unknown");
            }
            catch (Exception ex)
            {
                FLog.severe("Could not load build properties! Did you compile with NetBeans/Maven?");
                FLog.severe(ex);
            }
        }

        public String formattedVersion()
        {
            return pluginVersion + "." + number + " (" + head + ")";
        }
    }

    /**
     * This class is provided to please Codacy.
     */
    private final class Initializer
    {
        public Initializer()
        {
            initServices();
            initAdminUtils();
            initBridges();
            initFun();
            initHTTPD();
        }

        private void initServices()
        {
            // Start services
            serverInterface = new ServerInterface();
            gameRuleHandler = new GameRuleHandler();
            worldManager = new WorldManager();
            logViewer = new LogViewer();
            sql = new SQLite();
            adminList = new AdminList();
            activityLog = new ActivityLog();
            rankManager = new RankManager();
            commandBlocker = new CommandBlocker();
            eventBlocker = new EventBlocker();
            blockBlocker = new BlockBlocker();
            mobBlocker = new MobBlocker();
            interactBlocker = new InteractBlocker();
            potionBlocker = new PotionBlocker();
            loginProcess = new LoginProcess();
            antiNuke = new AntiNuke();
            antiSpam = new AntiSpam();
            playerList = new PlayerList();
            shop = new Shop();
            votifier = new Votifier();
            announcer = new Announcer();
            chatManager = new ChatHandler();
            discord = new Discord();
            punishmentList = new PunishmentList();
            banManager = new BanManager();
            indefiniteBanList = new IndefiniteBanList();
            permissionManager = new PermissionManager();
            signBlocker = new SignBlocker();
            entityWiper = new EntityWiper();
            sitter = new Sitter();
            vanishHandler = new VanishHandler();
            pterodactyl = new Pterodactyl();

            if (isPluginPresent("WorldGuard"))
                worldRestrictions = new WorldRestrictions();
        }

        private void initAdminUtils()
        {
            // Single admin utils
            commandSpy = new CommandSpy();
            cager = new Cager();
            freezer = new Freezer();
            orbiter = new Orbiter();
            muter = new Muter();
            editBlocker = new EditBlocker();
            pvpBlocker = new PVPBlocker();
            fuckoff = new Fuckoff();
            autoKick = new AutoKick();
            autoEject = new AutoEject();
            monitors = new Monitors();
        }

        private void initBridges()
        {
            // Start bridges
            if (isPluginPresent("BukkitTelnet"))
                bukkitTelnetBridge = new BukkitTelnetBridge();
            if (isPluginPresent("CoreProtect"))
                coreProtectBridge = new CoreProtectBridge();
            if (isPluginPresent("WorldGuard"))
                worldGuardBridge = new WorldGuardBridge();
            if (isPluginPresent("WorldEdit"))
                worldEditBridge = new WorldEditBridge();

            essentialsBridge = new EssentialsBridge();
            libsDisguisesBridge = new LibsDisguisesBridge();
            tfGuildsBridge = new TFGuildsBridge();

        }

        private void initFun()
        {
            // Fun
            itemFun = new ItemFun();
            landMiner = new Landminer();
            mp44 = new MP44();
            jumpPads = new Jumppads();
            trailer = new Trailer();
        }

        private void initHTTPD()
        {
            // HTTPD
            httpDaemon = new HTTPDaemon();
        }

        private boolean isPluginPresent(String plugin)
        {
            return TotalFreedomMod.getPlugin().getServer().getPluginManager().isPluginEnabled(plugin);
        }
    }

    public YamlConfiguration getSpigotConfig() {
        return spigotConfig;
    }
}