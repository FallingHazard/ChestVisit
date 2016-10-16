package net.sasha.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

import dagger.ObjectGraph;
import net.sasha.module.ChestFinderModule;

public class ChestFinderPlugin extends JavaPlugin{

  /* Creates a dependency graph for the Chest Finder tool */
  @Override
  public void onEnable() {
    ObjectGraph graph = ObjectGraph.create(new ChestFinderModule(this));
    graph.get(ChestFinder.class).onEnable();
    
    super.onEnable();
  }
  
}
