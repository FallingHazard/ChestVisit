package net.sasha.command;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dagger.Lazy;
import net.md_5.bungee.api.ChatColor;
import net.sasha.bukkit.BukkitChestManager;
import net.sasha.bukkit.ChestFinder;
import net.sasha.bukkit.ChestWorld;
import net.sasha.bukkit.IChestSpectateManager;

@Singleton
public class PreviousChestCommand implements CommandExecutor {
  private final Lazy<ChestFinder> lazyChestFinder;
  private final IChestSpectateManager spectateManager;
  
  @Inject
  public PreviousChestCommand(Lazy<ChestFinder> chestFinderPlugin, IChestSpectateManager manager) {
    lazyChestFinder = chestFinderPlugin;
    spectateManager = manager;
  }

  @Override
  public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
    if (sender instanceof Player) {
      if (!sender.isOp())
        return false;
      
      Player player = (Player) sender;
      UUID playerUID = player.getUniqueId();
      UUID worldUID = player.getWorld().getUID();
      
      ChestWorld spectatingCWorld = spectateManager.getSpecificChestWorld(playerUID, 
                                                                          worldUID);
      if (spectatingCWorld == null || !spectatingCWorld.isLoaded()) {
        BukkitChestManager chestManager = lazyChestFinder.get().getChestManager();
        spectatingCWorld = chestManager.getChestWorld(worldUID);
        
        spectateManager.setSpectating(playerUID, spectatingCWorld, worldUID);
      }
      
      if (spectatingCWorld.isLoaded()) {
        if (spectatingCWorld.hasPrevious()) {
          player.teleport(spectatingCWorld.getPrevious());
        }
        else {
          player.sendMessage(ChatColor.RED 
                             + "You have reached the first chest.");
        }
      }
      else {
        player.sendMessage(ChatColor.RED 
                           + "The chests in this world have not been loaded yet.");
      }
    }
    
    return false;
  }

}
