package net.sasha.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import net.sasha.bukkit.BukkitChestManager;
import net.sasha.bukkit.ChestFinder;
import net.sasha.bukkit.ChestFinderPlugin;
import net.sasha.bukkit.ChestSpectateManager;
import net.sasha.bukkit.IChestManager;
import net.sasha.bukkit.IChestSpectateManager;
import net.sasha.main.ChestLocator;
import net.sasha.main.IChestLocator;

@Module(injects = {ChestFinder.class})
public class ChestFinderModule {
  private final ChestFinderPlugin plugin;
  
  public ChestFinderModule(ChestFinderPlugin plugin) {
    this.plugin = plugin;
  }
  
  @Provides public ChestFinderPlugin providesChestFinderPlugin() {
    return plugin;
  }
  
  @Provides @Singleton public IChestSpectateManager 
    providesChestSpectateManager(ChestSpectateManager spectateManager) {
    return spectateManager;
  }
  
  @Provides @Singleton 
  public IChestManager providesChestManager(BukkitChestManager chestManager) {
    return chestManager;
  }
  
  @Provides @Singleton
  public IChestLocator providesChestLocator(ChestLocator chestLocator) {
    return chestLocator;
  }

}
