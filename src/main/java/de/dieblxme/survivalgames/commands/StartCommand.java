package de.dieblxme.survivalgames.commands;

import de.dieblxme.survivalgames.Main;
import de.dieblxme.survivalgames.server.GameStates;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.hasPermission(Main.main.startPerm)) {
            Main.main.countdown.startLobbyCountdown();
            Bukkit.broadcastMessage(Main.main.getPrefix() + ChatColor.GOLD + "The game force started! 60 seconds to start");
        }else {
            sender.sendMessage(Main.main.getPrefix() + ChatColor.RED + "You don't have permission to execute this command");
        }
        return false;
    }
}
