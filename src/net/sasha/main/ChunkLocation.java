package net.sasha.main;

public class ChunkLocation {
  private final int start;
  private final int size;
  
  public ChunkLocation(int newLocation, int newSize) {
    start = newLocation;
    size = newSize;
  }
  
  public ChunkLocation(AssocData assocLocationData) {
    byte[] offsetArray = new byte[4];
    
    offsetArray[0] = 0;
    offsetArray[1] = assocLocationData.readValue();
    offsetArray[2] = assocLocationData.readValue();
    offsetArray[3] = assocLocationData.readValue();
    
    byte sectorCount = assocLocationData.readValue();
    
    start = ChestDataMain.byteArrayToInt(offsetArray) * 4096;
    size = sectorCount * 4096;
  }
  
  public int getStart() {
    return start;
  }
  
  public int getSize() {
    return size;
  }
  
}
