package com.hardcourse.hardcoursecheckpoints.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

//import static com.hardcourse.hardcoursecheckpoints.Utils.PlayerCheckpoint.getCheckpointsForPlayer;

public class onRespawn implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        p.sendMessage("§c§lHARDCOURSE §fYou have respawned!");
        //getCheckpointsForPlayer(p.getUniqueId()).forEach(checkpointNumber -> p.sendMessage("§c§lHARDCOURSE §fYou have reached checkpoint §c" + checkpointNumber + "§f!"));
    }
}
