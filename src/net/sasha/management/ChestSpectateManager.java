package net.sasha.management;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.RequiredArgsConstructor;
import net.sasha.bukkit.ChestWorld;

/* Keeps track of the world and chest a  user is spectating */
@Singleton @RequiredArgsConstructor(onConstructor=@__(@Inject))
public class ChestSpectateManager {
  private final Map<UUID, Map<UUID, ChestWorld>> playerChestWorldMap 
    = new HashMap<UUID, Map<UUID, ChestWorld>>();
  
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
