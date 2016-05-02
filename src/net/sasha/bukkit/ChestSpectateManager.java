package net.sasha.bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ChestSpectateManager {
  private final Map<UUID, Map<UUID, ChestWorld>> playerChestWorldMap;
  
  public ChestSpectateManager() {
    playerChestWorldMap = new HashMap<UUID, Map<UUID, ChestWorld>>();
  }

  public Map<UUID, ChestWorld> getSpectatedChestWorlds(UUID uniqueId) {
    return playerChestWorldMap.get(uniqueId);
  }

  public void setSpectating(UUID spectatorId, ChestWorld toSpectate, UUID worldUID) {
    Map<UUID, ChestWorld> spectatedChestWorlds = getSpectatedChestWorlds(spectatorId);
    
    if (spectatedChestWorlds == null) {
      spectatedChestWorlds = new HashMap<UUID, ChestWorld>();
      playerChestWorldMap.put(spectatorId, spectatedChestWorlds);
    }
    
    spectatedChestWorlds.put(worldUID, toSpectate);
  }
  
  public ChestWorld getSpecificChestWorld(UUID spectatorId, UUID worldId) {
    Map<UUID, ChestWorld> spectatedChestWorlds = getSpectatedChestWorlds(spectatorId);
    
    if (spectatedChestWorlds == null) {
      spectatedChestWorlds = new HashMap<UUID, ChestWorld>();
      playerChestWorldMap.put(spectatorId, spectatedChestWorlds);
    }
    
    return spectatedChestWorlds.get(worldId);
  }
  
}
