package net.sasha.main;

import lombok.Getter;

/* Represents the location of a chunk in a region file */
public class ChunkLocation {
  @Getter private final int start;
  @Getter private final int size;
  
  public ChunkLocation(int newLocation, int newSize) {
    start = newLocation;
    size = newSize;
  }
  
  /* The first next 4 bytes represent the value of offset in
   * multiples of 4 kilobytes.
   * The fifth byte is the size of the chunk data also in multiples 
   * of 4 kilobytes.
   */
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
