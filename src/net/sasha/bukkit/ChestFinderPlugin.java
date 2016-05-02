package net.sasha.bukkit;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.sasha.command.ChestCommand;
import net.sasha.command.NextChestCommand;
import net.sasha.command.PreviousChestCommand;
import net.sasha.squadcraft.AddAllCommand;
import ninja.mcknight.squadcraft.chesttimer.ChestTimerPlugin;

public class ChestFinderPlugin extends JavaPlugin{
  private BukkitChestManager chestManager;
  private ChestSpectateManager spectateManager;

  @Override
  public void onDisable() {
    // TODO Auto-generated method stub
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
    
    Plugin chestTimer = getServer().getPluginManager().getPlugin("ChestTimer");
    
    if (chestTimer != null 
        && chestTimer.isEnabled() && chestTimer instanceof ChestTimerPlugin) {
      getCommand("addAll").setExecutor(new AddAllCommand((ChestTimerPlugin) chestTimer, this));
    }
    else {
      getCommand("addAll").setExecutor(new AddAllCommand(this));
    }
    
    chestManager = new BukkitChestManager(this);
    super.onEnable();
  }
  
  public BukkitChestManager getChestManager() {
    return chestManager;
  }
  
}
