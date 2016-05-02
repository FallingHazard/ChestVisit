package net.sasha.command;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.sasha.bukkit.ChestFinderPlugin;
import net.sasha.main.ChestDataMain;
import net.sasha.main.ChestLocation;

public class ChestCommand implements CommandExecutor {
  private final ChestFinderPlugin plugin;
  private boolean commandInProgess = false;

  public ChestCommand(ChestFinderPlugin chestFinderPlugin) {
    plugin = chestFinderPlugin;
  }

  @Override
  public boolean onCommand(CommandSender sender, 
                           Command arg1, String label, String[] args) {
    if (!sender.isOp())
      return false;
    
    if (args.length > 0) {
      String target = args[0];
      loadChestsFor(sender, target);
    }
    else if (sender instanceof Player) {
      Player cmdSender = (Player) sender;
      loadChestsFor(cmdSender, cmdSender.getWorld().getName());
    }
    
    return false;
  }
  
  @SuppressWarnings("deprecation")
  public void loadChestsFor(CommandSender sender, String target) {
    World targetWorld = plugin.getServer().getWorld(target);
    
    if (targetWorld != null) {     
      UUID targetUID = targetWorld.getUID();
      
      File[] serverFiles = plugin.getServer().getWorldContainer().listFiles();
    
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
          
          sender.sendMessage(ChatColor.RED + "Seaching for Chests in " + target + "...");
          String targetPath = targetFolder.getAbsolutePath();
          System.err.println(targetPath);
          
          targetWorld.save();
          
          plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, 
                                                                     new Runnable() {
            
            @Override
            public void run() {
              List<ChestLocation> chestLocations 
               = ChestDataMain.getChestLocs(targetPath);
              
              plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, 
                                                                        new Runnable() {
                
                @Override
                public void run() {
                  plugin.getChestManager().loadChestsInWorld(chestLocations, 
                                                             targetUID);
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

}
