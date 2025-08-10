package com.denied403.Hardcourse.Events;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static com.denied403.Hardcourse.Hardcourse.isDev;
import static com.denied403.Hardcourse.Utils.ColorUtil.Colorize;

public class onDrop implements Listener {
    @EventHandler
    public void onDrop(org.bukkit.event.player.PlayerDropItemEvent event) {
        if(event.getItemDrop().getItemStack().getType() == Material.CLOCK && event.getItemDrop().getItemStack().getItemMeta().displayName().equals(Colorize("&c&lStuck"))){
            event.setCancelled(true);
        }
        if(isDev()) {
            if (event.getItemDrop().getItemStack().getType() == Material.PAPER && event.getItemDrop().getItemStack().getItemMeta().displayName().equals(Colorize("&c&lPoints Shop"))) {
                event.setCancelled(true);
            }
        }
    }
}
