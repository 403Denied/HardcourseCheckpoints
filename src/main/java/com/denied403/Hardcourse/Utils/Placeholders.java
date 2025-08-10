package com.denied403.Hardcourse.Utils;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import static com.denied403.Hardcourse.Points.PointsManager.getPoints;

public class Placeholders extends PlaceholderExpansion {
    private static CheckpointDatabase database;
    public static void initalize(CheckpointDatabase db) {database = db;}
    @Override
    public @NotNull String getIdentifier() {return "hardcourse";}
    @Override
    public @NotNull String getAuthor() {return "403Denied";}
    @Override
    public @NotNull String getVersion() {return "1.0.0";}
    @Override
    public boolean persist(){return true;}
    @Override
    public String onRequest(OfflinePlayer player, String params){
        if(params.equalsIgnoreCase("points")){
            return String.valueOf(getPoints(player.getUniqueId()));
        }
        if(params.equalsIgnoreCase("level")){
            return String.valueOf(database.getLevel(player.getUniqueId())).replace(".0", "");
        }
        if(params.equalsIgnoreCase("season")){
            return String.valueOf(database.getSeason(player.getUniqueId()));
        }
        return null;
    }
}
