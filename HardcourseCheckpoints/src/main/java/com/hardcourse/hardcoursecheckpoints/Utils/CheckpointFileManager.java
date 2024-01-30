package com.hardcourse.hardcoursecheckpoints.Utils;

import com.hardcourse.hardcoursecheckpoints.HardcourseCheckpoints;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;

public class CheckpointFileManager {
    private static HardcourseCheckpoints plugin;
    private FileConfiguration checkpointsConfig = null;
    private File checkpointsSave = null;
    private static FileConfiguration playerCheckpointsConfig = null;
    private static File playerCheckpointsSave = null;

    public CheckpointFileManager(HardcourseCheckpoints plugin) {
        this.plugin = plugin;
        saveDefaultCheckpointsConfig();
    }

    public void reloadCheckpointsConfig() {
        if (checkpointsSave == null) {
            checkpointsSave = new File(plugin.getDataFolder(), "checkpoints.yml");
        }
        checkpointsConfig = YamlConfiguration.loadConfiguration(checkpointsSave);
        InputStream defaultStream = plugin.getResource("checkpoints.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            checkpointsConfig.setDefaults(defaultConfig);
        }
    }
    public static void reloadPlayerCheckpoints(){
        if (playerCheckpointsSave == null) {
            playerCheckpointsSave = new File(plugin.getDataFolder(), "playerCPs.yml");
        }
        playerCheckpointsConfig = YamlConfiguration.loadConfiguration(playerCheckpointsSave);
        InputStream defaultStream = plugin.getResource("playerCPs.yml");
        if (defaultStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultStream));
            playerCheckpointsConfig.setDefaults(defaultConfig);
        }
    }

    public FileConfiguration getCheckpointsConfig() {
        if (checkpointsConfig == null) {
            reloadCheckpointsConfig();
        }
        return this.checkpointsConfig;
    }
    public static FileConfiguration getPlayerCheckpointsConfig() {
        if (playerCheckpointsConfig == null) {
            reloadPlayerCheckpoints();
        }
        return playerCheckpointsConfig;
    }

    public void saveCheckpointsConfig() {
        if (checkpointsConfig == null || checkpointsSave == null) {
            return;
        }
        try {
            getCheckpointsConfig().save(checkpointsSave);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + checkpointsSave, e);
        }
    }
    public static void savePlayerCheckpointsConfig() {
        if (playerCheckpointsConfig == null || playerCheckpointsSave == null) {
            return;
        }
        try {
            getPlayerCheckpointsConfig().save(playerCheckpointsSave);
        } catch (Exception e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + playerCheckpointsSave, e);
        }
    }

    public void saveDefaultCheckpointsConfig() {
        if (checkpointsSave == null) {
            checkpointsSave = new File(plugin.getDataFolder(), "checkpoints.yml");
        }
        if (!checkpointsSave.exists()) {
            plugin.saveResource("checkpoints.yml", false);
        }
    }
    public void saveDefaultPlayerCheckpointsConfig() {
        if (playerCheckpointsSave == null) {
            playerCheckpointsSave = new File(plugin.getDataFolder(), "playerCPs.yml");
        }
        if (!playerCheckpointsSave.exists()) {
            plugin.saveResource("playerCPs.yml", false);
        }
    }

}
