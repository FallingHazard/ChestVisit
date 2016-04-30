package net.sasha.bukkit;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
import net.sasha.main.ChestDataMain;
import net.sasha.main.ChestLocation;

public class ChestCommand implements CommandExecutor {
  private final ChestFinderPlugin plugin;
  private boolean commandInProgess = false;

  public ChestCommand(ChestFinderPlugin chestFinderPlugin) {
    plugin = chestFinderPlugin;
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean onCommand(CommandSender sender, Command arg1, String label, String[] args) {
    if (args.length > 0) {
      String target = args[0];
      World targetWorld = plugin.getServer().getWorld(target);
      
      if (targetWorld != null) {     
        UUID targetUID = targetWorld.getUID();
        
        File[] serverFiles = plugin.getServer().getWorldContainer().listFiles();
        
        System.err.println(plugin.getServer().getWorldContainer().getAbsolutePath());
        File targetFolder = null;
        int index = 0;
        boolean found = false;
        
        while (!found && index < serverFiles.length) {
          File someFile = serverFiles[index];
          
          if (someFile.getName().equals(target)) {
            found = true;
            targetFolder = someFile;
          }
          index ++;
        }
        
        if (!commandInProgess) {
          if (found) {
            commandInProgess = true;
            
            sender.sendMessage(ChatColor.RED + "Seaching for Chests...");
            String targetPath = targetFolder.getAbsolutePath();
            
            targetWorld.save();
            
            plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new Runnable() {
              
              @Override
              public void run() {
                List<ChestLocation> chestLocations = ChestDataMain.getChestLocs(targetPath);
                
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                  
                  @Override
                  public void run() {
                    plugin.getChestManager().loadChestsInWorld(chestLocations, targetUID);
                    commandInProgess = false;
                  }
                }, 0L);
              }
              
            }, 0L);
          }
          else {
            sender.sendMessage("World file not found");
          }
        }
        else {
          sender.sendMessage("Chest search already in progress!");
        }
      } 
      else {
        sender.sendMessage("No world found");
      }
    }
    return false;
  }

}
