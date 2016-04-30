package net.sasha.main;

public class ChestLocation {
  private final int x;
  private final int y;
  private final int z;
 
  public ChestLocation(int newX, int newY, int newZ) {
    x = newX;
    y = newY;
    z = newZ;
  }
  
  @Override
  public String toString() {
    return "x:" +x + "#y: " + y +"#z: " + z;
  }

  public int getX() {
    return x;
  }
  
  public int getY() {
    return y;
  }
  
  public int getZ() {
    return z;
  }

}
