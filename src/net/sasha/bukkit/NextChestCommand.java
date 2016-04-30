package net.sasha.bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class NextChestCommand implements CommandExecutor {
  private final ChestFinderPlugin plugin;
  
  private final Map<UUID, ChestWorld> playerVisitingMap;
  
  public NextChestCommand(ChestFinderPlugin chestFinderPlugin) {
    plugin = chestFinderPlugin;
    playerVisitingMap = new HashMap<UUID, ChestWorld>();
  }

  @Override
  public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      
      ChestWorld currentlyVisitng = playerVisitingMap.get(player.getUniqueId());
      
      if (currentlyVisitng == null) {
        BukkitChestManager chestManager = plugin.getChestManager();
        currentlyVisitng = chestManager.getChestWorld(player.getWorld().getUID());
        
        playerVisitingMap.put(player.getUniqueId(), currentlyVisitng);
      }
      
      if (currentlyVisitng != null) {
        player.teleport(currentlyVisitng.getNext());
      }
      else {
        player.sendMessage(ChatColor.RED 
                           + "The chests from this world ain't been loaded yet!");
      }
    }
    
    return false;
  }

}
