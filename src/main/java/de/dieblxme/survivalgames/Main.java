package de.dieblxme.survivalgames;

import de.dieblxme.survivalgames.commands.LocationCommand;
import de.dieblxme.survivalgames.commands.StartCommand;
import de.dieblxme.survivalgames.listener.PlayerEvents;
import de.dieblxme.survivalgames.listener.ServerChatListener;
import de.dieblxme.survivalgames.server.Countdown;
import de.dieblxme.survivalgames.server.FileManager;
import de.dieblxme.survivalgames.server.GameStates;
import de.dieblxme.survivalgames.server.SGChests;
import de.dieblxme.survivalgames.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Main extends JavaPlugin {
    public GameStates state;
    public ArrayList<Player> playersAlive;
    public static Main main;
    private String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "SurvivalGames" + ChatColor.DARK_GRAY + "] ";
    public FileManager fileManager;
    public int minPlayers, maxPlayers;
    public Countdown countdown;
    public Utils utils;
    public SGChests chests;
    public String gameID;
    public String locationPerm;
    public String startPerm;
    public String allPerm;

    public int lobbyTime = 60, ingameTime = 60 * 20, restartTime = 15;

    @Override
    public void onEnable() {
        System.out.println(prefix + ChatColor.GOLD + "Starting " + ChatColor.YELLOW + "SurivalGames " + ChatColor.RED  + "v1.0 " + ChatColor.GOLD + "by " + ChatColor.BLUE + "DieBlxme");
        System.out.println(prefix + ChatColor.GOLD + "Please check if your config is up to date! " + ChatColor.BLUE + "./plugins/SurvivalGames/config.yml " + ChatColor.GOLD + " Set the spawn locations with /location");
        main = this;
        state = GameStates.LOBBY;
        playersAlive = new ArrayList<>();
        fileManager = new FileManager();
        fileManager.initConfig();
        countdown = new Countdown();
        utils = new Utils();
        chests = new SGChests();
        chests.register();
        gameID = utils.createGameUUID();
        register();
    }

    @Override
    public void onDisable() {

    }

    private void register() {
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerEvents(), this);
        pluginManager.registerEvents(new SGChests(), this);
        pluginManager.registerEvents(new ServerChatListener(), this);
        this.getCommand("location").setExecutor(new LocationCommand());
        this.getCommand("start").setExecutor(new StartCommand());
    }

    public String getPrefix() {
        return prefix;
    }
}
