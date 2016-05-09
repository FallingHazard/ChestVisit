package net.sasha.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import net.sasha.bukkit.ChestFinder;
import net.sasha.bukkit.ChestFinderPlugin;
import net.sasha.bukkit.ChestSpectateManager;
import net.sasha.bukkit.IChestSpectateManager;

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

}
