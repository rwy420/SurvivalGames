package de.dieblxme.survivalgames.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public abstract class ScoreboardBuilder {
    private final Scoreboard scoreboard;
    private final Objective objective;
    public final Player player;

    public ScoreboardBuilder(Player player, String displayName) {
        this.player = player;
        if(player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
        this.scoreboard = player.getScoreboard();
        if(this.scoreboard.getObjective("display") != null) {
            this.scoreboard.getObjective("display").unregister();
        }
        this.objective = this.scoreboard.registerNewObjective("display", "display", displayName);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        createScoreboard();
    }

    public abstract void createScoreboard();

    public abstract void update();

    public void setDisplayName(String displayName) {
        this.objective.setDisplayName(displayName);
    }

    public void addContent(String content, int score) {
        this.objective.getScore(content).setScore(score);
    }

    public void removeContent(String content) {
        this.scoreboard.resetScores(content);
    }
}
