package net.sasha.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class ChestFinderPlugin extends JavaPlugin{
  private BukkitChestManager chestManager;

  @Override
  public void onDisable() {
    // TODO Auto-generated method stub
    super.onDisable();
  }

  @Override
  public void onEnable() {
    getCommand("findchests").setExecutor(new ChestCommand(this));
    getCommand("nextchest").setExecutor(new NextChestCommand(this));
    getCommand("previouschest").setExecutor(new PreviousChestCommand(this));
    chestManager = new BukkitChestManager(this);
    super.onEnable();
  }
  
  public BukkitChestManager getChestManager() {
    return chestManager;
  }
  
}
