package de.dieblxme.survivalgames.commands;

import de.dieblxme.survivalgames.Main;
import de.dieblxme.survivalgames.server.FileManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LocationCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission(Main.main.locationPerm) || sender.hasPermission(Main.main.allPerm)) {
            int position;
            Location location;
            if(args.length == 1) {
                if(sender instanceof Player) {
                    Player player = (Player) sender;
                    position = Integer.parseInt(args[0]);
                    location = player.getLocation();
                    Main.main.fileManager.setLocation(position, location);
                }else {
                    sender.sendMessage(Main.main.getPrefix() + ChatColor.RED + "You need to be a player to execute this command");
                }
            }else {
                sender.sendMessage(Main.main.getPrefix() + ChatColor.RED + "Usage: /location [number of spawn-place]");
            }
        }else {
            sender.sendMessage(Main.main.getPrefix() + ChatColor.RED + "You don't have permission to execute this command");
        }
        return false;
    }
}
