package com.hardcourse.hardcoursecheckpoints.Commands;

import com.hardcourse.hardcoursecheckpoints.HardcourseCheckpoints;
import com.hardcourse.hardcoursecheckpoints.Utils.CheckpointData;
import com.hardcourse.hardcoursecheckpoints.Utils.CheckpointFileManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MakeCPCommand implements CommandExecutor {
    private JavaPlugin plugin;
    private List<CheckpointData> season_one_checkpoints = new ArrayList<>();
    public CheckpointFileManager data;

    public MakeCPCommand(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("makecp").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        data = new CheckpointFileManager(HardcourseCheckpoints.getPlugin(HardcourseCheckpoints.class));

        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lHARDCOURSE §fYou must be a player to use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (player.isOp()) {
            Block block = player.getLocation().getBlock();
            Block jukebox = block.getRelative(0, -1, 0);

            if (jukebox.getType() == Material.JUKEBOX && block.getType() == Material.OAK_SIGN) {
                Sign sign = (Sign) block.getState();
                String line = sign.getLine(0);

                if (!line.isEmpty() && line.matches("\\d+")) {
                    int checkpointNumber = Integer.parseInt(line);

                    if (!checkpointExistsInWorld(player.getWorld().getName(), checkpointNumber)) {
                        CheckpointData checkpointData = new CheckpointData(checkpointNumber, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getWorld().getName());
                        season_one_checkpoints.add(checkpointData);
                        saveCheckpoints();
                        player.sendMessage("§c§lHARDCOURSE §fCheckpoint §c" + checkpointNumber + "§f successfully set!");
                    } else {
                        player.sendMessage("§c§lHARDCOURSE §fA checkpoint with the same level number already exists in the world.");
                    }
                } else {
                    player.sendMessage("§c§lHARDCOURSE §fYou must have a number on the first line of the sign!");
                }
            } else {
                player.sendMessage("§c§lHARDCOURSE §fYou must be standing on a sign above a jukebox!");
            }

        } else {
            player.sendMessage("§c§lHARDCOURSE §fYou must be an operator to use this command!");
        }

        return true;
    }

    private boolean checkpointExistsInWorld(String world, int checkpointNumber) {
        for (CheckpointData checkpointData : season_one_checkpoints) {
            if (checkpointData.getWorld().equals(world) && checkpointData.getLevel() == checkpointNumber) {
                return true;
            }
        }
        return false;
    }

    private void saveCheckpoints() {
        List<Map<String, Object>> checkpointMaps = new ArrayList<>();
        for (CheckpointData checkpointData : season_one_checkpoints) {
            checkpointMaps.add(checkpointData.toMap());
        }
        data.getCheckpointsConfig().set("Season1", checkpointMaps);
        data.saveCheckpointsConfig();
    }

    private void loadCheckpoints() {
        List<?> rawCheckpointMaps = data.getCheckpointsConfig().getMapList("Season1");
        season_one_checkpoints.clear();

        for (Object rawCheckpointMap : rawCheckpointMaps) {
            if (rawCheckpointMap instanceof Map) {
                @SuppressWarnings("unchecked")
                Map<String, Object> checkpointMap = (Map<String, Object>) rawCheckpointMap;
                season_one_checkpoints.add(CheckpointData.fromMap(checkpointMap));
            }
        }
    }

}
