package com.hardcourse.hardcoursecheckpoints;
import com.hardcourse.hardcoursecheckpoints.Commands.MakeCPCommand;
import com.hardcourse.hardcoursecheckpoints.Events.*;
import com.hardcourse.hardcoursecheckpoints.Utils.CheckpointFileManager;
import com.hardcourse.hardcoursecheckpoints.Utils.PlayerCheckpoint;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class HardcourseCheckpoints extends JavaPlugin {


    public static Plugin getPlugin() {
        return HardcourseCheckpoints.getPlugin(HardcourseCheckpoints.class);
    }
    public CheckpointFileManager data;

    @Override
    public void onEnable() {
        this.data = new CheckpointFileManager(this);
        CheckpointFileManager checkpointFileManager = new CheckpointFileManager(this);
        PlayerCheckpoint playerCheckpoint = new PlayerCheckpoint(checkpointFileManager);
        JavaPlugin plugin = this;
        onWalk walkListener = new onWalk(plugin, playerCheckpoint);
        new MakeCPCommand(this);
        getServer().getPluginManager().registerEvents(new onRespawn(), this);

    }

    @Override
    public void onDisable() {
    }
}
