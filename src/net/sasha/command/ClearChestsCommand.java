package net.sasha.command;

import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import lombok.RequiredArgsConstructor;
import net.sasha.bukkit.ChestWorld;
import net.sasha.management.ChestManager;

@Singleton  @RequiredArgsConstructor(onConstructor=@__(@Inject))
public class ClearChestsCommand implements CommandExecutor {
  private final ChestManager chestManager;

  @Override
  public boolean onCommand(CommandSender sender, Command arg1, String arg2,
      String[] arg3) {
    if (sender instanceof Player) {
      if (sender.isOp()) {
        Player cmdSender = (Player) sender;
        UUID worldUID = cmdSender.getWorld().getUID();
        ChestWorld cWorldToClear = chestManager.getChestWorld(worldUID);
        
        if (cWorldToClear.isLoaded()) {
          cWorldToClear.emptyChests();
        }
        else {
          cmdSender.sendMessage("Load chests first");
        }
      }
    }
    return false;
  }

}
