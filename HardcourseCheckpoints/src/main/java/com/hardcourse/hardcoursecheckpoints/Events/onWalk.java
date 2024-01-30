package com.hardcourse.hardcoursecheckpoints.Events;

import com.hardcourse.hardcoursecheckpoints.Utils.CheckpointFileManager;
import com.hardcourse.hardcoursecheckpoints.Utils.PlayerCheckpoint;
import com.hardcourse.hardcoursecheckpoints.Utils.WarningSender;
import com.sun.tools.javac.comp.Check;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class onWalk implements Listener {
    private final PlayerCheckpoint playerCheckpoint;
    private final JavaPlugin plugin;

    public onWalk(JavaPlugin plugin, PlayerCheckpoint playerCheckpoint) {
        this.plugin = plugin;
        this.playerCheckpoint = playerCheckpoint;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Block block = p.getLocation().getBlock();
        Block jukebox = block.getRelative(0, -1, 0);

        if (jukebox.getType() == Material.JUKEBOX && block.getType() == Material.OAK_SIGN) {
            Sign s = (Sign) block.getState();
            String line = s.getLine(0);

            if (!line.isEmpty() && !line.matches(".*[a-zA-Z].*")) {
                int checkpointNumber = Integer.parseInt(line);
                UUID playerUUID = p.getUniqueId();
                int currentHighestCheckpoint = playerCheckpoint.getHighestCheckpointForPlayer(playerUUID);
                List<Integer> checkpointsForPlayer = PlayerCheckpoint.getCheckpointsForPlayer(playerUUID);

                if (checkpointNumber > currentHighestCheckpoint) {
                    if (isValidCheckpoint(checkpointsForPlayer, checkpointNumber)) {
                        playerCheckpoint.setHighestCheckpointForPlayer(playerUUID, checkpointNumber);
                        logPlayerCheckpoint(playerUUID, checkpointNumber, p.getWorld().getName());
                        p.sendMessage("§c§lHARDCOURSE §fNew highest checkpoint reached: §c" + checkpointNumber + "§f in world §c" + p.getWorld().getName() + "§f!");
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1.0f, 1.0f);
                    } else {
                        // Cheating detected
                        p.sendMessage("§c§lHARDCOURSE §r§4Cheating detected! A staff member has been notified.");
                        // Add any additional actions you want to take for cheating detection
                        //WarningSender.sendWarning(checkpointsForPlayer.get(checkpointsForPlayer.size() - 1), checkpointNumber, p.getName());
                    }
                }
            }
        }
    }

    private void logPlayerCheckpoint(UUID playerUUID, int checkpointNumber, String world) {
        // Log the highest checkpoint and its world to the playerCPs.yml file
        CheckpointFileManager.getPlayerCheckpointsConfig().set(playerUUID + ".highestCheckpoint", checkpointNumber);
        CheckpointFileManager.getPlayerCheckpointsConfig().set(playerUUID + ".world", world);
        CheckpointFileManager.savePlayerCheckpointsConfig();
    }

    private boolean isValidCheckpoint(List<Integer> checkpointsForPlayer, int newCheckpointNumber) {
        if (checkpointsForPlayer.isEmpty()) {
            return newCheckpointNumber <= 2;
        } else {
            int lastCheckpoint = checkpointsForPlayer.get(checkpointsForPlayer.size() - 1);
            return newCheckpointNumber <= lastCheckpoint + 2;
        }
    }
}
