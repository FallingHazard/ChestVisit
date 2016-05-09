package net.sasha.management;

import java.util.List;
import java.util.UUID;

import net.sasha.bukkit.ChestWorld;
import net.sasha.main.ChestLocation;

public interface IChestManager {

  public ChestWorld getChestWorld(UUID uid);
  
  public void loadChestsInWorld(List<ChestLocation> chestlocs, UUID worldUID);
}
