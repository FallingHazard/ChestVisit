package net.sasha.management;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;

import dagger.Lazy;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import net.sasha.bukkit.ChestFinder;
import net.sasha.bukkit.ChestWorld;
import net.sasha.main.ChestLocation;
import net.sasha.main.IChestLocator;

@Singleton @RequiredArgsConstructor(onConstructor=@__({@Inject}))
public class ChestManager implements IChestManager{
  private final Map<UUID, ChestWorld> chestWorlds 
   = new HashMap<UUID, ChestWorld>();
  
  private final Lazy<ChestFinder> chestFinder;
  
  /* Will overrite any previous chests loaded to that world */
  public void loadChestsInWorld(List<ChestLocation> chestLocs, UUID worldUID) {
    Server server = chestFinder.get().getServer();
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
