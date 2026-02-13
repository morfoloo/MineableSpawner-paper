package com.vinden.MineableSpawner;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class SpawnerBreak implements Listener {

    private final JavaPlugin plugin;

    public SpawnerBreak(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.SPAWNER) return;

        Player player = event.getPlayer();
        ItemStack tool = player.getInventory().getItemInMainHand();

        if (tool == null || !tool.containsEnchantment(Enchantment.SILK_TOUCH)) {
            event.setDropItems(false);
            return;
        }

        event.setDropItems(false);

        CreatureSpawner spawner = (CreatureSpawner) block.getState();

        Material spawnEgg = switch (spawner.getSpawnedType()) {
            case ZOMBIE -> Material.ZOMBIE_SPAWN_EGG;
            case SKELETON -> Material.SKELETON_SPAWN_EGG;
            case SPIDER -> Material.SPIDER_SPAWN_EGG;
            case CAVE_SPIDER -> Material.CAVE_SPIDER_SPAWN_EGG;
            case SILVERFISH -> Material.SILVERFISH_SPAWN_EGG;
            case BLAZE -> Material.BLAZE_SPAWN_EGG;
            case MAGMA_CUBE -> Material.MAGMA_CUBE_SPAWN_EGG;
            default -> spawnEgg = null;
        };

        ItemStack spawnerItem = new ItemStack(Material.SPAWNER);
        BlockStateMeta meta = (BlockStateMeta) spawnerItem.getItemMeta();
        if (meta != null) {
            CreatureSpawner newSpawner = (CreatureSpawner) meta.getBlockState();
            newSpawner.setSpawnedType(spawner.getSpawnedType());
            meta.setBlockState(newSpawner);
            spawnerItem.setItemMeta(meta);
        }
        block.getWorld().dropItemNaturally(block.getLocation(), spawnerItem);
        if (spawnEgg != null) {
            block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(spawnEgg));
        }
        block.setType(Material.AIR);
    }
}
