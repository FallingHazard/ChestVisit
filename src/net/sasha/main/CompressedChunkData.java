package net.sasha.main;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

/* Uses a chunk location to extract chunk data from region data and form
 * an instance.
 */
public class CompressedChunkData {
  private byte[] compressedData;
  
  public CompressedChunkData(byte[] regionData, ChunkLocation chunkLoc) {
    int chunkStart = chunkLoc.getStart();
    int chunkEnd = chunkStart + chunkLoc.getSize();
    
    if (chunkStart != chunkEnd) {
      //byte[] chunkArray = Arrays.copyOfRange(regionData, chunkStart, chunkEnd + 1);
      
      /* Strips the first 5 bytes off ( the header) */
      compressedData = Arrays.copyOfRange(regionData, chunkStart  + 5, chunkEnd + 1);
    }
  }
  
  public InflaterInputStream getDataStream() {
    if (compressedData != null)
      return new InflaterInputStream(new ByteArrayInputStream(compressedData), new Inflater());
    else
      return null;
  }

}
