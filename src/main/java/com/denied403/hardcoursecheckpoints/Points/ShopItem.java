package com.denied403.hardcoursecheckpoints.Points;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShopItem implements Listener, CommandExecutor {

    // Gives the player the "Points Shop" paper in hotbar slot 4 (middle slot)
    public static void givePointsShopPaper(Player player) {
        // Avoid giving duplicates
        for (ItemStack item : player.getInventory().getContents()) {
            if (isPointsShopPaper(item)) {
                return;
            }
        }

        ItemStack paper = new ItemStack(Material.PAPER);
        ItemMeta meta = paper.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lPoints Shop"));
            paper.setItemMeta(meta);
        }

        player.getInventory().setItem(4, paper); // 4 = 5th slot (0-based)
    }

    // Check if the item is the special shop paper
    public static boolean isPointsShopPaper(ItemStack item) {
        if (item == null || item.getType() != Material.PAPER) return false;
        ItemMeta meta = item.getItemMeta();
        return meta != null && ChatColor.stripColor(meta.getDisplayName()).equals("Points Shop");
    }

    // Prevent dropping the shop paper
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (isPointsShopPaper(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    // Handle the /shop command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        givePointsShopPaper(player);
        player.sendMessage(ChatColor.GREEN + "You have received the Points Shop paper.");
        return true;
    }
}
