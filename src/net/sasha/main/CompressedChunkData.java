package net.sasha.main;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

public class CompressedChunkData {
  private byte[] compressedData;
  
  public CompressedChunkData(byte[] regionData, ChunkLocation chunkLoc) {
    int chunkStart = chunkLoc.getStart();
    int chunkEnd = chunkStart + chunkLoc.getSize();
    
    if (chunkStart != chunkEnd) {
      byte[] chunkArray = Arrays.copyOfRange(regionData, chunkStart, chunkEnd + 1);
      
      byte[] lengthArray = new byte[4];
      lengthArray[0] = chunkArray[0];
      lengthArray[1] = chunkArray[1];
      lengthArray[2] = chunkArray[2];
      lengthArray[3] = chunkArray[3];
      
      ChestLocator.byteArrayToInt(lengthArray);
      compressedData = Arrays.copyOfRange(chunkArray, 5, chunkArray.length);
    }
  }
  
  public InflaterInputStream getDataStream() {
    if (compressedData != null)
      return new InflaterInputStream(new ByteArrayInputStream(compressedData), new Inflater());
    else
      return null;
  }

}
