package net.sasha.main;

import lombok.Getter;

/* An immutable 3D location */
public class ChestLocation {
  @Getter private final int x;
  @Getter private final int y;
  @Getter private final int z;
 
  public ChestLocation(int newX, int newY, int newZ) {
    x = newX;
    y = newY;
    z = newZ;
  }
  
  @Override
  public String toString() {
    return "x:" +x + "#y: " + y +"#z: " + z;
  }

}
