package net.sasha.bukkit;

import java.util.List;

import org.bukkit.Location;

public class ChestWorld {
  private final List<Location> chestLocations;
  private int pointer = 0;
  
  public ChestWorld(List<Location> chestLocs) {
    chestLocations = chestLocs;
  }
  
  public boolean hasNext() {
    return pointer < chestLocations.size();
  }
  
  public Location getNext() {
    Location nextChestLoc = chestLocations.get(pointer);
    pointer ++;
    
    return nextChestLoc;
  }
  
  public boolean hasPrevious() {
    return pointer > 0 && chestLocations.size() > 0;
  }
  
  public Location getPrevious() {
    pointer --;
    
    Location previousChestLoc = chestLocations.get(pointer);
    return previousChestLoc;
  }

  public ChestWorld copy() {
    return new ChestWorld(chestLocations);
  }

}
