package net.sasha.management;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Lazy;
import lombok.RequiredArgsConstructor;
import net.sasha.bukkit.ChestFinder;
import net.sasha.bukkit.ChestWorld;
import net.sasha.main.IChestLocator;

@Singleton @RequiredArgsConstructor(onConstructor=@__(@Inject))
public class ChestSpectateManager implements IChestSpectateManager{
  private final Map<UUID, Map<UUID, ChestWorld>> playerChestWorldMap 
    = new HashMap<UUID, Map<UUID, ChestWorld>>();
  
  @Override
  public Map<UUID, ChestWorld> getSpectatedChestWorlds(UUID uniqueId) {
    return playerChestWorldMap.get(uniqueId);
  }

  @Override
  public void setSpectating(UUID spectatorId, ChestWorld toSpectate, UUID worldUID) {
    Map<UUID, ChestWorld> spectatedChestWorlds = getSpectatedChestWorlds(spectatorId);
    
    if (spectatedChestWorlds == null) {
      spectatedChestWorlds = new HashMap<UUID, ChestWorld>();
      playerChestWorldMap.put(spectatorId, spectatedChestWorlds);
    }
    
    spectatedChestWorlds.put(worldUID, toSpectate);
  }
  
  @Override
  public ChestWorld getSpecificChestWorld(UUID spectatorId, UUID worldId) {
    Map<UUID, ChestWorld> spectatedChestWorlds = getSpectatedChestWorlds(spectatorId);
    
    if (spectatedChestWorlds == null) {
      spectatedChestWorlds = new HashMap<UUID, ChestWorld>();
      playerChestWorldMap.put(spectatorId, spectatedChestWorlds);
    }
    
    return spectatedChestWorlds.get(worldId);
  }
  
}
