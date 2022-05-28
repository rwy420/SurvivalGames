package de.dieblxme.survivalgames.server;

import de.dieblxme.survivalgames.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FileManager {
    public File configFile = new File("plugins/" + Main.main.getDataFolder().getName(), "config.yml");
    public FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    public void initConfig() {
        if(config.getString("Game.MinimalPlayerCount") == null) {
            config.set("Game.MinimalPlayerCount", 2);
        }
        if(config.getString("Game.MaximalPlayerCount") == null) {
            config.set("Game.MaximalPlayerCount", 8);
        }
        Main.main.minPlayers = config.getInt("Game.MinimalPlayerCount");
        Main.main.maxPlayers = config.getInt("Game.MaximalPlayerCount");

        if(config.getString("Perms.LocationPerm") == null) {
            config.set("Perms.LocationPerm", "sg.perms.location");
        }
        if(config.getString("Perms.StartPerm") == null) {
            config.set("Perms.StartPerm", "sg.perms.start");
        }
        if(config.getString("Perms.Admin") == null) {
            config.set("Perms.Admin", "sg.perms.admin");
        }

        Main.main.locationPerm = config.getString("Perms.LocationPerm");
        Main.main.startPerm = config.getString("Perms.LocationPerm");
        Main.main.allPerm = config.getString("Perms.Admin");
        saveConfig();
    }

    public void setLocation(int plot, Location location) {
        config.set("Spawns." + (int)plot + ".World", location.getWorld().getName());
        config.set("Spawns." + (int)plot + ".X", (int) location.getX());
        config.set("Spawns." + (int)plot + ".Y", (int) location.getY());
        config.set("Spawns." + (int)plot + ".Z", (int) location.getZ());
        saveConfig();
    }

    public Location getLocation(int plot) {
        if(config.get("Spawns." + plot + ".X") != null) {
            Location location;

            String worldName = (String) config.get("Spawns." + plot + ".World");
            World world = Bukkit.getWorld(worldName);
            double x = Double.parseDouble(config.getString("Spawns." + plot + ".X")) + 0.5;
            double y = Double.parseDouble(config.getString("Spawns." + plot + ".Y")) + 0.5;
            double z = Double.parseDouble(config.getString("Spawns." + plot + ".Z")) + 0.5;

            location = new Location(world, x, y, z);
            return location;
        }else {
            return null;
        }
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            System.out.println(Main.main.getPrefix() + Color.RED + "Sorry! Something went wrong saving the config file!");
            e.printStackTrace();
        }
    }
}
