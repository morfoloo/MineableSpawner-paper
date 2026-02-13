package com.vinden.MineableSpawner;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MineableSpawner extends JavaPlugin implements Listener {
  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
    getLogger().info("Plugin MineableSpawner is working now!");
    Bukkit.getPluginManager().registerEvents(new SpawnerBreak(this), this);
  }
}