package net.sasha.bukkit;

import javax.inject.Inject;

import org.bukkit.Server;
import lombok.Getter;
import net.sasha.command.ChestCommand;
import net.sasha.command.NextChestCommand;
import net.sasha.command.PreviousChestCommand;

public class ChestFinder{
  @Getter private final ChestFinderPlugin plugin;
  
  private final ChestCommand chestCommand;
  private final NextChestCommand nextChestCmd;
  private final PreviousChestCommand prevChestCmd;
  
  @Inject
  public ChestFinder(ChestFinderPlugin plugin, 
                     ChestCommand chestCommand,
                     NextChestCommand nextChestCmd,
                     PreviousChestCommand prevChestCmd) {
    
    this.chestCommand = chestCommand;
    this.nextChestCmd = nextChestCmd;
    this.prevChestCmd = prevChestCmd;
    this.plugin = plugin;
  }
  
  public void onEnable() {
    plugin.getCommand("findchests").setExecutor(chestCommand);
    
    plugin.getCommand("nextchest")
     .setExecutor(nextChestCmd);
    
    plugin.getCommand("previouschest")
     .setExecutor(prevChestCmd);
  }

  public Server getServer() {
    return plugin.getServer();
  }
  
  public void scheduleSyncDelayedJob(Runnable toRun, long delay) {
    getServer().getScheduler().scheduleSyncDelayedTask(plugin, toRun, delay);
  }
  
  public void scheduleAsyncDelayedJob(Runnable toRun, long delay) {
    getServer().getScheduler().scheduleAsyncDelayedTask(plugin, toRun, delay);
  }
    
}
