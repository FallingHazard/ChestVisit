package net.sasha.module;

import javax.inject.Singleton;

import org.bukkit.Server;

import dagger.Module;
import dagger.Provides;
import net.sasha.bukkit.ChestFinder;
import net.sasha.bukkit.ChestFinderPlugin;

@Module(injects = {ChestFinder.class})
public class ChestFinderModule {
  private final ChestFinderPlugin plugin;
  
  public ChestFinderModule(ChestFinderPlugin plugin) {
    this.plugin = plugin;
  }
  
  @Provides public ChestFinderPlugin providesChestFinderPlugin() {
    return plugin;
  }
  
  @Provides @Singleton public Server providesServer() {
    return plugin.getServer();
  }

}
