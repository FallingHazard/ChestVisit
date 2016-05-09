package net.sasha.management;

import java.util.Map;
import java.util.UUID;

import net.sasha.bukkit.ChestWorld;

public interface IChestSpectateManager {
  public Map<UUID, ChestWorld> getSpectatedChestWorlds(UUID uniqueId);
  
  public void setSpectating(UUID spectatorId, ChestWorld toSpectate, UUID worldUID);
  
  public ChestWorld getSpecificChestWorld(UUID spectatorId, UUID worldId);
}
