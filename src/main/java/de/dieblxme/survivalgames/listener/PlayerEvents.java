package de.dieblxme.survivalgames.listener;

import de.dieblxme.survivalgames.Main;
import de.dieblxme.survivalgames.scoreboard.Scoreboard;
import de.dieblxme.survivalgames.server.GameStates;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        if(Main.main.state != GameStates.LOBBY) {
            event.getPlayer().kickPlayer(ChatColor.RED + "The game already started");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        int players = Bukkit.getOnlinePlayers().size();
        if(players >= Main.main.minPlayers) {
            Main.main.countdown.startLobbyCountdown();
        }
        new Scoreboard(event.getPlayer());
        Location location = Main.main.fileManager.getLocation(players);
        if(location != null) {
            event.getPlayer().teleport(location);
            event.setJoinMessage(Main.main.getPrefix() + ChatColor.GOLD + event.getPlayer().getDisplayName() + " joined the game! " + Bukkit.getOnlinePlayers().size() + "/8");
            Main.main.utils.clearPlayer(event.getPlayer());
            Main.main.playersAlive.add(event.getPlayer());
            event.getPlayer().setBedSpawnLocation(location);
        }else {
            event.setJoinMessage(" ");
            if(event.getPlayer().hasPermission(Main.main.locationPerm) || event.getPlayer().hasPermission(Main.main.allPerm)) {
                event.getPlayer().sendMessage(Main.main.getPrefix() + ChatColor.RED + "No spawn location is set!");
                event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());
            }else {
                event.getPlayer().kickPlayer(ChatColor.RED + "Your spawn location isn't set! Please concat the Server Administration.");
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if(Main.main.state == GameStates.LOBBY && !event.getPlayer().hasPermission(Main.main.locationPerm) && !event.getPlayer().hasPermission(Main.main.allPerm)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        event.setMaxPlayers(Main.main.maxPlayers);
        event.setMotd(ChatColor.BOLD + "" + ChatColor.GOLD + "SurvivalGames " + Main.main.state + "\n" + ChatColor.YELLOW + Bukkit.getOnlinePlayers().size() + " Players online!");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(Main.main.getPrefix() + ChatColor.GOLD + event.getPlayer().getDisplayName() + " left the game! " + Bukkit.getOnlinePlayers().size() + "/8");
        if(Main.main.playersAlive.contains(event.getPlayer())) {
            Main.main.playersAlive.remove(event.getPlayer());
        }
        if(Main.main.state == GameStates.INGAME) {

        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(!event.getPlayer().hasPermission(Main.main.allPerm)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if(!event.getPlayer().hasPermission(Main.main.allPerm)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if(!event.getPlayer().hasPermission(Main.main.allPerm)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if(event.getEntity() instanceof Player) {
            Main.main.playersAlive.remove(event.getEntity());
            if(event.getEntity().getKiller() != null) {
                event.setDeathMessage(Main.main.getPrefix() + ChatColor.GOLD + event.getEntity().getDisplayName() + " was killed by " + event.getEntity().getKiller().getDisplayName());
            }else {
                event.setDeathMessage(Main.main.getPrefix() + ChatColor.GOLD + event.getEntity().getDisplayName() + " died");
            }
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.hidePlayer(event.getEntity());
            });
            event.getEntity().getPlayer().spigot().respawn();
            ItemStack leave = new ItemStack(Material.RED_CONCRETE, 1);
            ItemMeta leaveMeta = leave.getItemMeta();
            leaveMeta.setDisplayName(ChatColor.GOLD + "Leave Game");
            leave.setItemMeta(leaveMeta);
            event.getEntity().getInventory().addItem(leave);
            event.getEntity().setGameMode(GameMode.ADVENTURE);
            event.getEntity().setFlying(true);
            if(Main.main.playersAlive.size() >= 1) {
                Player winner = Main.main.playersAlive.get(0);
                winner.getInventory().addItem(leave);
                winner.setGameMode(GameMode.ADVENTURE);
                winner.setFlying(true);
                winner.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, winner.getLocation(), 20);
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(!Main.main.playersAlive.contains(event.getPlayer()) && event.getPlayer().getItemInHand().equals(Material.RED_CONCRETE)) {
            event.getPlayer().kickPlayer(ChatColor.GOLD + "Thank you for playing");
        }
    }
}
