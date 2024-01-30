package com.hardcourse.hardcoursecheckpoints.Utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerCheckpoint {
    private static CheckpointFileManager checkpointFileManager = null;

    public PlayerCheckpoint(CheckpointFileManager checkpointFileManager) {
        PlayerCheckpoint.checkpointFileManager = checkpointFileManager;
    }

    // Helper method to add a checkpoint for a specific player
    public void addCheckpointForPlayer(Player player, int checkpointNumber) {
        UUID playerUUID = player.getUniqueId();
        List<Integer> checkpoints = getCheckpointsForPlayer(playerUUID);

        checkpoints.add(checkpointNumber);
        CheckpointFileManager.getPlayerCheckpointsConfig().set(playerUUID.toString() + ".checkpoints", checkpoints);
        CheckpointFileManager.savePlayerCheckpointsConfig();
    }

    // Helper method to set the highest checkpoint for a specific player
    public void setHighestCheckpointForPlayer(UUID player, int checkpointNumber) {
        List<Integer> checkpoints = new ArrayList<>();
        checkpoints.add(checkpointNumber);

        CheckpointFileManager.getPlayerCheckpointsConfig().set(player.toString() + ".checkpoints", checkpoints);
        CheckpointFileManager.savePlayerCheckpointsConfig();
    }

    // Helper method to retrieve checkpoints for a specific player
    public static List<Integer> getCheckpointsForPlayer(UUID playerUUID) {
        FileConfiguration config = CheckpointFileManager.getPlayerCheckpointsConfig();
        if (config.contains(playerUUID.toString() + ".checkpoints")) {
            return config.getIntegerList(playerUUID.toString() + ".checkpoints");
        } else {
            return new ArrayList<>();
        }
    }

    // Helper method to retrieve the highest checkpoint for a specific player
    public int getHighestCheckpointForPlayer(UUID playerUUID) {
        List<Integer> checkpoints = getCheckpointsForPlayer(playerUUID);
        return checkpoints.isEmpty() ? 0 : checkpoints.get(checkpoints.size() - 1);
    }
}
