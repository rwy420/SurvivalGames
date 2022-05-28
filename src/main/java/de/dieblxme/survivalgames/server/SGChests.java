package de.dieblxme.survivalgames.server;

import de.dieblxme.survivalgames.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class SGChests implements Listener {

    private HashMap<Location, Inventory> chests;
    private ArrayList<ItemStack> loot;

    public SGChests() {
        loot = new ArrayList<>();
        chests = new HashMap<>();
        loot.add(Main.main.utils.create(Material.APPLE, Main.main.utils.generateRandomNumber(2, 7)));
        loot.add(Main.main.utils.create(Material.BREAD, Main.main.utils.generateRandomNumber(1, 5)));
        loot.add(Main.main.utils.create(Material.WOODEN_SWORD, 1));
        loot.add(Main.main.utils.create(Material.STONE_SWORD, 1));
        loot.add(Main.main.utils.create(Material.IRON_SWORD, 1));
        loot.add(Main.main.utils.create(Material.GOLDEN_SWORD, 1));
        loot.add(Main.main.utils.create(Material.FISHING_ROD, 1));
        loot.add(Main.main.utils.create(Material.LEATHER_BOOTS, 1));
        loot.add(Main.main.utils.create(Material.LEATHER_LEGGINGS, 1));
        loot.add(Main.main.utils.create(Material.LEATHER_CHESTPLATE, 1));
        loot.add(Main.main.utils.create(Material.LEATHER_HELMET, 1));
        loot.add(Main.main.utils.create(Material.IRON_BOOTS, 1));
        loot.add(Main.main.utils.create(Material.IRON_LEGGINGS, 1));
        loot.add(Main.main.utils.create(Material.IRON_CHESTPLATE, 1));
        loot.add(Main.main.utils.create(Material.IRON_HELMET, 1));
        loot.add(Main.main.utils.create(Material.GOLDEN_BOOTS, 1));
        loot.add(Main.main.utils.create(Material.GOLDEN_LEGGINGS, 1));
        loot.add(Main.main.utils.create(Material.GOLDEN_CHESTPLATE, 1));
        loot.add(Main.main.utils.create(Material.GOLDEN_HELMET, 1));
        loot.add(Main.main.utils.create(Material.DIAMOND_CHESTPLATE, 1));
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.NOTE_BLOCK && Main.main.playersAlive.contains(event.getPlayer())) {
            event.setCancelled(true);
            if(chests.containsKey(event.getClickedBlock().getLocation())) {
                event.getPlayer().openInventory(chests.get(event.getClickedBlock().getLocation()));
            }else {
                Inventory chest = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Chest");
                for (int i = 0; i < Main.main.utils.generateRandomNumber(5, 7); i++) {
                    chest.setItem(Main.main.utils.generateRandomNumber(1, chest.getSize() - 1), loot.get(Main.main.utils.generateRandomNumber(1, loot.size() - 1)));
                }
                chests.put(event.getClickedBlock().getLocation(), chest);
                event.getPlayer().openInventory(chest);
            }
        }
    }

    public void register() {

    }
}
