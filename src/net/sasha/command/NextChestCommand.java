package net.sasha.command;

import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.sasha.bukkit.BukkitChestManager;
import net.sasha.bukkit.ChestFinderPlugin;
import net.sasha.bukkit.ChestSpectateManager;
import net.sasha.bukkit.ChestWorld;

public class NextChestCommand implements CommandExecutor {
  private final ChestFinderPlugin plugin;
   
  private final ChestSpectateManager spectateManager;
  
  public NextChestCommand(ChestFinderPlugin chestFinderPlugin, ChestSpectateManager manager) {
    plugin = chestFinderPlugin;
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
        BukkitChestManager chestManager = plugin.getChestManager();
        spectatingCWorld = chestManager.getChestWorld(worldUID);
        
        spectateManager.setSpectating(playerUID, spectatingCWorld, worldUID);
      }
      
      if (spectatingCWorld.isLoaded()) {
        if (spectatingCWorld.hasNext()) {
          player.teleport(spectatingCWorld.getNext());
        }
        else {
          player.sendMessage(ChatColor.RED 
                             + "You have reached the last chest.");
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