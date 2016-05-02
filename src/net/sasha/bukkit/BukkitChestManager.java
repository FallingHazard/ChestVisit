package net.sasha.bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import net.md_5.bungee.api.ChatColor;
import net.sasha.main.ChestLocation;

public class BukkitChestManager {
  private final Map<UUID, ChestWorld> chestWorlds;
  private final ChestFinderPlugin plugin;
  
  public BukkitChestManager(ChestFinderPlugin chestFinderPlugin) {
    chestWorlds = new HashMap<UUID, ChestWorld>();
    plugin = chestFinderPlugin;
  }
  
  /* Will overrite any previous chests loaded to that world */
  public void loadChestsInWorld(List<ChestLocation> chestLocs, UUID worldUID) {
    Server server = plugin.getServer();
    server.broadcastMessage(ChatColor.RED + "Loading chests...");
    
    World targetWorld = server.getWorld(worldUID);
    
    List<Location> chestLocations = new ArrayList<Location>();
    for (ChestLocation chestLoc : chestLocs) {
      chestLocations.add(new Location(targetWorld, 
                                      chestLoc.getX(), 
                                      chestLoc.getY(), 
                                      chestLoc.getZ()));
    }
    server.broadcastMessage(ChatColor.RED 
        + "Loaded " + chestLocations.size() + " chests!");
    
    chestWorlds.put(worldUID, new ChestWorld(chestLocations));
  }

  public ChestWorld getChestWorld(UUID uid) {
    ChestWorld someChestWorld = chestWorlds.get(uid);
    
    if (someChestWorld != null)
      return someChestWorld.copy();
    else
      return new ChestWorld(null);
  }
  
}
