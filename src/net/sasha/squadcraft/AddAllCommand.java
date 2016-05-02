package net.sasha.squadcraft;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.sasha.bukkit.ChestFinderPlugin;
import net.sasha.bukkit.ChestWorld;
import net.sasha.exception.NotAChestException;
import ninja.mcknight.squadcraft.chesttimer.ChestTimerPlugin;

public class AddAllCommand implements CommandExecutor {
  private ChestTimerPlugin chestTimerPlugin;
  private final ChestFinderPlugin chestFinderPlugin;

  public AddAllCommand(ChestFinderPlugin main) {
    this(null, main);
  }
  
  public AddAllCommand(ChestTimerPlugin plugin, ChestFinderPlugin main) {
    chestTimerPlugin = plugin;
    chestFinderPlugin = main;
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
    if (!sender.isOp())
      return false;
    
    if (chestTimerPlugin != null) {
      if (sender instanceof Player) {
        Player cmdSender = (Player) sender;
        World world = cmdSender.getWorld();
        
        ChestWorld chestWorld
         = chestFinderPlugin.getChestManager().getChestWorld(world.getUID());
        
        if (chestWorld.isLoaded()) {
          try {
            sender.sendMessage(ChatColor.RED 
                               + "Adding tier 1 in " + world.getName() + " ...");
            
            while (chestWorld.hasNext()) {
              Location chestLoc = chestWorld.getNext();
              BlockState chestState = world.getBlockAt(chestLoc).getState();
              
              if (chestState instanceof Chest) {
                Chest chest = (Chest) chestState;
                
                chestTimerPlugin.getChestTimer().getAddChestCmd().addChestToTier1(chest);
              }
              else {
                throw new NotAChestException("Not a chest...");
              }
            }
          } catch(NotAChestException e) {
            System.err.println(e);
            chestFinderPlugin.getServer().broadcastMessage(ChatColor.RED 
                                                           + "Chest error..");
          } catch(IllegalArgumentException e2) {
            sender.sendMessage(ChatColor.RED + "Please add Tier 1 first!");
          }
        }
        else {
          sender.sendMessage(ChatColor.RED + "Chests in this world are not loaded!");
        }
      }
    }
    else {
      sender.sendMessage(ChatColor.RED + "No Chest Timer not loaded..");
    }
    
    return false;
  }

}
