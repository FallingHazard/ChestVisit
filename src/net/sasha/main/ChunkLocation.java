package net.sasha.main;

import lombok.Getter;

public class ChunkLocation {
  @Getter private final int start;
  @Getter private final int size;
  
  public ChunkLocation(int newLocation, int newSize) {
    start = newLocation;
    size = newSize;
  }
  
  public ChunkLocation(AssocByteArray assocLocationData) {
    byte[] offsetArray = new byte[4];
    
    offsetArray[0] = 0;
    offsetArray[1] = assocLocationData.readValue();
    offsetArray[2] = assocLocationData.readValue();
    offsetArray[3] = assocLocationData.readValue();
    
    byte sectorCount = assocLocationData.readValue();
    
    start = ChestLocator.byteArrayToInt(offsetArray) * 4096;
    size = sectorCount * 4096;
  }
  
}
