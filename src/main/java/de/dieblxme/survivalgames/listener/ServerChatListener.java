package de.dieblxme.survivalgames.listener;

import de.dieblxme.survivalgames.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ServerChatListener implements Listener {

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if(Main.main.playersAlive.contains(event.getPlayer())) {
            event.setMessage(ChatColor.GREEN + event.getPlayer().getDisplayName() + ChatColor.DARK_GRAY + " : " + ChatColor.GOLD + event.getMessage());
        }else {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if(!Main.main.playersAlive.contains(player)) {
                    player.sendMessage(ChatColor.GRAY + "[" + ChatColor.RED + "X" + ChatColor.GRAY + "]" + "" + ChatColor.GREEN + event.getPlayer().getDisplayName() + ChatColor.GRAY + " : " + ChatColor.GOLD + event.getMessage());
                }
            });
        }
    }
}
