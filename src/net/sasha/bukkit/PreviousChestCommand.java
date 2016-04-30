package net.sasha.bukkit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PreviousChestCommand implements CommandExecutor {
  private final ChestFinderPlugin plugin;

  public PreviousChestCommand(ChestFinderPlugin chestFinderPlugin) {
    plugin = chestFinderPlugin;
  }

  @Override
  public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
    // TODO Auto-generated method stub
    return false;
  }

}
