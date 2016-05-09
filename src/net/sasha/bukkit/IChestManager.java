package net.sasha.bukkit;

import java.util.List;
import java.util.UUID;

import net.sasha.main.ChestLocation;

public interface IChestManager {

  public ChestWorld getChestWorld(UUID uid);
  
  public void loadChestsInWorld(List<ChestLocation> chestlocs, UUID worldUID);
}
