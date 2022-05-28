package de.dieblxme.survivalgames.scoreboard;

import de.dieblxme.survivalgames.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Scoreboard extends ScoreboardBuilder {
    public Scoreboard(Player player) {
        super(player, ChatColor.GOLD.toString() + ChatColor.BOLD + "   Survival Games   ");
    }

    @Override
    public void createScoreboard() {
        addContent(ChatColor.RED + " ", 8);
        addContent(ChatColor.GRAY.toString() + "IP", 7);
        addContent(ChatColor.DARK_GRAY.toString() + "->" + ChatColor.LIGHT_PURPLE + player.getAddress(), 6);
        addContent(ChatColor.RED + " ", 5);
        addContent(ChatColor.GRAY.toString() + "Players Online", 4);
        addContent(ChatColor.DARK_GRAY.toString() + "->" + ChatColor.BLUE + String.valueOf(Bukkit.getOnlinePlayers().size()) + ChatColor.GRAY + " of " + ChatColor.BLUE + String.valueOf(Main.main.maxPlayers), 3);
        addContent(ChatColor.RED + " ", 2);
        addContent(ChatColor.GRAY.toString() + "Found a bug?", 1);
        addContent(ChatColor.DARK_GRAY.toString() + "->" + ChatColor.LIGHT_PURPLE + "https://dieblxme.de/bug", 0);

    }

    @Override
    public void update() {

    }
}
