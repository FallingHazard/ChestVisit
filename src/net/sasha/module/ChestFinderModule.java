package net.sasha.module;

import javax.inject.Singleton;

import org.bukkit.Server;

import dagger.Module;
import dagger.Provides;
import net.sasha.bukkit.ChestFinder;
import net.sasha.bukkit.ChestFinderPlugin;
import net.sasha.main.ChestLocator;
import net.sasha.main.IChestLocator;
import net.sasha.management.ChestManager;
import net.sasha.management.ChestSpectateManager;
import net.sasha.management.IChestManager;
import net.sasha.management.IChestSpectateManager;

@Module(injects = {ChestFinder.class})
public class ChestFinderModule {
  private final ChestFinderPlugin plugin;
  
  public ChestFinderModule(ChestFinderPlugin plugin) {
    this.plugin = plugin;
  }
  
  @Provides public ChestFinderPlugin providesChestFinderPlugin() {
    return plugin;
  }
  
  @Provides @Singleton 
  public IChestSpectateManager 
    providesChestSpectateManager(ChestSpectateManager spectateManager) {
    return spectateManager;
  }
  
  @Provides @Singleton 
  public IChestManager providesChestManager(ChestManager chestManager) {
    return chestManager;
  }
  
  @Provides @Singleton
  public IChestLocator providesChestLocator(ChestLocator chestLocator) {
    return chestLocator;
  }
  
  @Provides @Singleton public Server providesServer() {
    return plugin.getServer();
  }

}
