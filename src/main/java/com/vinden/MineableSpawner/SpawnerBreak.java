package com.vinden.MineableSpawner;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
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
        Player player = event.getPlayer();

        if (block.getType() == Material.SPAWNER) {
            event.setDropItems(false);
            CreatureSpawner spawner = (CreatureSpawner) block.getState();
            ItemStack spawnerItem = new ItemStack(Material.SPAWNER);
            BlockStateMeta meta = (BlockStateMeta) spawnerItem.getItemMeta();
            if (meta != null && spawner.getSpawnedType() != null) {
                ((CreatureSpawner) meta.getBlockState()).setSpawnedType(spawner.getSpawnedType());
                spawnerItem.setItemMeta(meta);
            }
            block.getWorld().dropItemNaturally(block.getLocation(), spawnerItem);
            if (spawner.getSpawnedType() != null) {
                Material spawnEgg;
                switch (spawner.getSpawnedType()) {
                    case ZOMBIE -> spawnEgg = Material.ZOMBIE_SPAWN_EGG;
                    case SKELETON -> spawnEgg = Material.SKELETON_SPAWN_EGG;
                    case SPIDER -> spawnEgg = Material.SPIDER_SPAWN_EGG;
                    case CAVE_SPIDER -> spawnEgg = Material.CAVE_SPIDER_SPAWN_EGG;
                    case SILVERFISH -> spawnEgg = Material.SILVERFISH_SPAWN_EGG;
                    case BLAZE -> spawnEgg = Material.BLAZE_SPAWN_EGG;
                    case MAGMA_CUBE -> spawnEgg = Material.MAGMA_CUBE_SPAWN_EGG;
                    default -> spawnEgg = null;
                }
                if (spawnEgg != null) {
                    block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(spawnEgg));
                }
            }
            block.setType(Material.AIR);
        }
    }
}
