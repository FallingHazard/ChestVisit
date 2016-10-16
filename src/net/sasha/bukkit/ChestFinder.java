package net.sasha.bukkit;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bukkit.Server;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.sasha.command.ClearChestsCommand;
import net.sasha.command.FindChestsCommand;
import net.sasha.command.NextChestCommand;
import net.sasha.command.PreviousChestCommand;

/* Represents an instance of the Chest Finder tool */
@Singleton
@RequiredArgsConstructor(onConstructor=@__(@Inject))
public class ChestFinder{
  private final ChestFinderPlugin plugin;
  
  private final FindChestsCommand chestCommand;
  private final NextChestCommand nextChestCmd;
  private final PreviousChestCommand prevChestCmd;
  private final ClearChestsCommand clearChestCmd;
  
  @Getter private final Server server;
    
  public void onEnable() {
    plugin.getCommand("findchests").setExecutor(chestCommand);
    
    plugin.getCommand("nextchest").setExecutor(nextChestCmd);
    
    plugin.getCommand("previouschest").setExecutor(prevChestCmd);
    
    plugin.getCommand("clearallchests").setExecutor(clearChestCmd);
  }

  public void scheduleSyncDelayedJob(Runnable toRun, long delay) {
    getServer().getScheduler().scheduleSyncDelayedTask(plugin, toRun, delay);
  }
  
  @SuppressWarnings("deprecation")
  public void scheduleAsyncDelayedJob(Runnable toRun, long delay) {
    getServer().getScheduler().scheduleAsyncDelayedTask(plugin, toRun, delay);
  }
    
}
