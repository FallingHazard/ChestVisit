package net.sasha.command;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dagger.Lazy;
import net.md_5.bungee.api.ChatColor;
import net.sasha.bukkit.ChestFinder;
import net.sasha.main.IChestLocator;
import net.sasha.management.IChestManager;
import net.sasha.main.ChestLocation;

@Singleton
public class ChestCommand implements CommandExecutor {
  private final Lazy<ChestFinder> lazyChestFinder;
  private final IChestLocator chestLocator;
  private final IChestManager chestManager;

  @Inject
  public ChestCommand(Lazy<ChestFinder> chestFinder, IChestLocator locator, IChestManager manager) {
    lazyChestFinder = chestFinder;
    chestLocator = locator;
    chestManager = manager;
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
  
  public void loadChestsFor(CommandSender sender, String target) {
    ChestFinder chestFinder = lazyChestFinder.get();
    World targetWorld = chestFinder.getServer().getWorld(target);
    
    if (targetWorld != null) {     
      UUID targetUID = targetWorld.getUID();
      
      File[] serverFiles = chestFinder.getServer().getWorldContainer().listFiles();
    
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
      
      if (!chestLocator.isInUse()) {
        chestLocator.setInUse(true);
        if (found) {
          sender.sendMessage(ChatColor.RED + "Seaching for Chests in " + target + "...");
          String targetPath = targetFolder.getAbsolutePath();
          System.err.println(targetPath);
          
          targetWorld.save();
          
          chestFinder.scheduleAsyncDelayedJob(new Runnable() {
            
            @Override
            public void run() {
              List<ChestLocation> chestLocations 
               = chestLocator.getChestLocs(targetPath);
              
              chestFinder.scheduleSyncDelayedJob(new Runnable() {
                
                @Override
                public void run() {
                  chestManager.loadChestsInWorld(chestLocations, 
                                                                  targetUID);
                  chestLocator.setInUse(false);
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
