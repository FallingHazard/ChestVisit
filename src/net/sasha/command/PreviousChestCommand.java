package net.sasha.command;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.sasha.bukkit.ChestWorld;
import net.sasha.management.ChestManager;
import net.sasha.management.ChestSpectateManager;

@Singleton @RequiredArgsConstructor(onConstructor=@__(@Inject))
public class PreviousChestCommand implements CommandExecutor {
  private final ChestSpectateManager spectateManager;
  private final ChestManager chestManager;
  
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
