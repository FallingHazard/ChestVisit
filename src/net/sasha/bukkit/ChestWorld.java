package net.sasha.bukkit;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Chest;

public class ChestWorld {
  private final List<Location> chestLocations;
  private int pointer = 0;
  private final boolean loaded;
  
  public ChestWorld(List<Location> chestLocs) {
    chestLocations = chestLocs;
    
    loaded = chestLocs == null ? false : true;
  }
  
  public boolean hasNext() {
    return pointer < chestLocations.size();
  }
  
  public boolean isLoaded() {
    return loaded;
  }
  
  public Location getNext() {
    Location nextChestLoc = chestLocations.get(pointer);
    pointer ++;
    
    return nextChestLoc;
  }
  
  public boolean hasPrevious() {
    return pointer > 1 && chestLocations.size() > 0;
  }
  
  public Location getPrevious() {
    pointer -= 2;
    
    Location previousChestLoc = chestLocations.get(pointer);
    return previousChestLoc;
  }

  public ChestWorld copy() {
    return new ChestWorld(chestLocations);
  }

  public void emptyChests() {
    for (Location chestLoc : chestLocations) {
      Chest chest = (Chest) chestLoc.getBlock().getState();
      chest.getBlockInventory().clear();
    }
  }

}
