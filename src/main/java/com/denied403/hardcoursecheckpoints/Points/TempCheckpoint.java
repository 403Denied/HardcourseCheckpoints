package com.denied403.hardcoursecheckpoints.Points;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TempCheckpoint implements Listener {

    private boolean isTemporaryCheckpointBook(ItemStack item) {
        if (item == null || item.getType() != Material.BOOK || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null || meta.getDisplayName() == null) return false;

        return ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase("Temporary Checkpoint");
    }

    @EventHandler
    public void onPlayerUseTempCheckpoint(PlayerInteractEvent event) {
        // Only handle right-click block action with main hand
        if (!event.getAction().toString().contains("RIGHT_CLICK_BLOCK")) return;
        if (event.getHand() != EquipmentSlot.HAND) return; // ignore off-hand

        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) return; // just in case

        ItemStack itemInHand = player.getInventory().getItemInMainHand();

        if (!isTemporaryCheckpointBook(itemInHand)) return;

        // Set respawn location to the block clicked, slightly above it
        Location respawnLocation = clickedBlock.getLocation().add(0.5, 1, 0.5);
        player.setBedSpawnLocation(respawnLocation, true);

        // Remove one Temporary Checkpoint book from player's main hand
        if (itemInHand.getAmount() > 1) {
            itemInHand.setAmount(itemInHand.getAmount() - 1);
            player.getInventory().setItemInMainHand(itemInHand);
        } else {
            player.getInventory().setItemInMainHand(null);
        }

        player.sendMessage(ChatColor.GREEN + "Temporary checkpoint set!");

        event.setCancelled(true);
    }
}
