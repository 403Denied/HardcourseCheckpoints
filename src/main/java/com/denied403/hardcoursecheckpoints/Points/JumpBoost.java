package com.denied403.hardcoursecheckpoints.Points;

import com.denied403.hardcoursecheckpoints.HardcourseCheckpoints;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.event.inventory.InventoryType.SlotType;

public class JumpBoost implements Listener {

    private final HardcourseCheckpoints plugin;

    public JumpBoost(HardcourseCheckpoints plugin) {
        this.plugin = plugin;
    }

    private boolean isJumpBoostBoot(ItemStack item) {
        if (item == null || item.getType() != Material.LEATHER_BOOTS || !item.hasItemMeta()) return false;
        String name = ChatColor.stripColor(item.getItemMeta().getDisplayName());
        return name != null && name.equalsIgnoreCase("Jump Boost");
    }

    // Right-click usage of the boots
    @EventHandler
    public void onRightClickJumpBoot(PlayerInteractEvent event) {
        if (!event.hasItem()) return;

        ItemStack item = event.getItem();
        if (!isJumpBoostBoot(item)) return;

        Player player = event.getPlayer();
        event.setCancelled(true);

        consumeItem(player, item);
        applyJumpBoost(player);
    }

    // Putting boots into boots armor slot
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            ItemStack boots = player.getInventory().getBoots();
            if (isJumpBoostBoot(boots)) {
                player.getInventory().setBoots(null); // Remove the boots
                consumeItem(player, boots);
                applyJumpBoost(player);
            }
        }, 1L); // Delay by 1 tick to allow equip
    }

    // Helper method to reduce item amount or remove
    private void consumeItem(Player player, ItemStack item) {
        if (item == null) return;
        int amount = item.getAmount();
        if (amount > 1) {
            item.setAmount(amount - 1);
        } else {
            player.getInventory().removeItem(item);
        }
    }

    // Apply jump boost and notify player
    private void applyJumpBoost(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP_BOOST, 200, 0));
        player.sendMessage(ChatColor.GREEN + "You used Jump Boost!");
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.2f);
    }
}
