package de.dieblxme.survivalgames.server;

import de.dieblxme.survivalgames.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Countdown {
     public int lobbyCountdown, gameCountdown, restartCountdown;

    public void startLobbyCountdown() {
        lobbyCountdown = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.main, new Runnable() {
            @Override
            public void run() {
                if(Main.main.lobbyTime >= 1) {
                    if(Main.main.lobbyTime == 60 || Main.main.lobbyTime == 30 || Main.main.lobbyTime == 15 || Main.main.lobbyTime <= 10) {
                        Bukkit.broadcastMessage(Main.main.getPrefix() + ChatColor.GOLD + "The game starts in " + Main.main.lobbyTime + " seconds");
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            player.setLevel(Main.main.lobbyTime);
                        });
                    }
                }else if(Main.main.lobbyTime == 0) {
                    Bukkit.broadcastMessage(Main.main.getPrefix()+ ChatColor.GOLD + "Game started! Good luck");
                    Main.main.state = GameStates.INGAME;
                    Bukkit.getScheduler().cancelTask(lobbyCountdown);
                    startGameCountdown();
                }
                Main.main.lobbyTime--;
            }
        }, 0, 20);
    }

    public void startGameCountdown() {
        gameCountdown = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.main, new Runnable() {
            @Override
            public void run() {
                if(Main.main.ingameTime >= 1) {
                    if(Main.main.ingameTime == 60 || Main.main.ingameTime == 30 || Main.main.ingameTime == 15 || Main.main.ingameTime <= 10) {
                        Bukkit.broadcastMessage(Main.main.getPrefix() + ChatColor.GOLD + "The game ends in " + Main.main.ingameTime + " seconds");
                        Bukkit.getOnlinePlayers().forEach(player -> {
                            player.setLevel(Main.main.ingameTime);
                        });
                    }
                }else if(Main.main.lobbyTime == 0) {
                    Bukkit.broadcastMessage(Main.main.getPrefix() + ChatColor.GOLD + "The game ended");
                    Main.main.state = GameStates.RESTART;
                    Bukkit.getScheduler().cancelTask(gameCountdown);
                    startRestartCountdown();
                }
                Main.main.ingameTime--;
            }
        }, 0, 20);
    }

    public void startRestartCountdown() {
        restartCountdown = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.main, new Runnable() {
            @Override
            public void run() {
                if(Main.main.restartTime >= 1) {
                    if(Main.main.restartTime == 15 || Main.main.restartTime == 10 || Main.main.restartTime <= 5) {
                        Bukkit.broadcastMessage(Main.main.getPrefix() + ChatColor.GOLD + "Server restart in " + Main.main.restartTime + " seconds");
                    }
                }else if(Main.main.restartTime == 0) {
                    Bukkit.broadcastMessage(Main.main.getPrefix() + ChatColor.GOLD + "Server restart");
                    Bukkit.getOnlinePlayers().forEach(player -> {
                        player.kickPlayer(ChatColor.RED + "Server restart");
                    });
                    Bukkit.getScheduler().cancelTask(restartCountdown);
                    Bukkit.spigot().restart();
                }
                Main.main.restartTime--;
            }
        }, 0, 20);
    }
}
