package de.dieblxme.survivalgames.utils;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;
import java.util.UUID;

public class Utils {

    public ItemStack create(Material material, int amount) {
        return new ItemStack(material, amount);
    }

    public ItemStack createItemMeta(Material material, int amount, short id, String displayName) {
        ItemStack itemStack = new ItemStack(material, amount, id);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public void clearPlayer(Player p) {
        p.getActivePotionEffects().forEach(potionEffect -> {
            p.removePotionEffect(potionEffect.getType());
        });
        p.getInventory().clear();
        p.setHealth(20.0);
        p.setFoodLevel(20);
        p.setLevel(0);
        p.setExp(0);
        p.getInventory().setArmorContents(null);
    }

    public int generateRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max-min) + min; //This line works (tbh i dont know how);
    }

    public String createGameUUID() {
        UUID uuid = UUID.randomUUID();
        String[] parts = uuid.toString().split("-");
        return parts[0];
    }
}
