package net.sasha.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import net.sasha.command.ChestCommand;
import net.sasha.command.NextChestCommand;
import net.sasha.command.PreviousChestCommand;

public class ChestFinderPlugin extends JavaPlugin{
  private BukkitChestManager chestManager;
  private ChestSpectateManager spectateManager;

  @Override
  public void onDisable() {
    super.onDisable();
  }

  @Override
  public void onEnable() {
    spectateManager = new ChestSpectateManager();
    
    getCommand("findchests").setExecutor(new ChestCommand(this));
    
    getCommand("nextchest")
     .setExecutor(new NextChestCommand(this,spectateManager));
    
    getCommand("previouschest")
     .setExecutor(new PreviousChestCommand(this, spectateManager));
    
    chestManager = new BukkitChestManager(this);
    super.onEnable();
  }
  
  public BukkitChestManager getChestManager() {
    return chestManager;
  }
  
}
