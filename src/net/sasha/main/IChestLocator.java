package net.sasha.main;

import java.util.List;

public interface IChestLocator {
  public List<ChestLocation> getChestLocs(String path);
  
  public boolean isInUse();
  
  public void setInUse(boolean value);
}
