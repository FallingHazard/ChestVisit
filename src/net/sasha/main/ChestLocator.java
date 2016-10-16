package net.sasha.main;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.zip.InflaterInputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.jnbt.CompoundTag;
import org.jnbt.IntTag;
import org.jnbt.ListTag;
import org.jnbt.NBTInputStream;
import org.jnbt.StringTag;
import org.jnbt.Tag;

import lombok.Getter;
import lombok.Setter;

/* Tool for finding a List of Bukkit Locations of all chests in a world */
@Singleton
public class ChestLocator {
  @Getter @Setter private boolean inUse = false;

  @Inject
  public ChestLocator() {}
 
  public static List<ChestLocation> getChestLocs(String pathToWorldFolder) {  
    File regionFolder = new File(pathToWorldFolder + File.separator + "region");
    File[] regionFiles = regionFolder.listFiles();
    
    if (regionFiles == null) {
      regionFolder = new File(pathToWorldFolder 
                              + File.separator 
                              + "DIM-1" + File.separator + "region");
      regionFiles = regionFolder.listFiles();
    }
    
    List<ChestLocation> chestLocations = new ArrayList<ChestLocation>();
    
    int counter = 0;
    for(File regionFile : regionFiles) {
      NBTInputStream nbtStream = null;
      
      try {
        byte[] regionData = Files.readAllBytes(regionFile.toPath());
        
        byte[] locationData = Arrays.copyOfRange(regionData, 0, 4096);
        AssocByteArray assocLocationData = new AssocByteArray(locationData);
        
        List<CompressedChunkData> chunksData = new ArrayList<CompressedChunkData>();
        while(assocLocationData.hasNext()) {
          ChunkLocation chunkLoc = new ChunkLocation(assocLocationData);
          
          chunksData.add(new CompressedChunkData(regionData, chunkLoc));
        }
        
        for (CompressedChunkData data : chunksData) {
          InflaterInputStream dataStream = data.getDataStream();
          
          if (dataStream != null) {
            nbtStream = new NBTInputStream(dataStream, false);
            
            Tag nbtTag = nbtStream.readTag();
            
            if(nbtTag instanceof CompoundTag) {
              CompoundTag compoundNBT = (CompoundTag) nbtTag;
              CompoundTag level = (CompoundTag) compoundNBT.getValue().get("Level");
              
              ListTag tileEntities = (ListTag) level.getValue().get("TileEntities");
              List<Tag> tileEntityList = tileEntities.getValue();
              
              for (Tag tile : tileEntityList) {
                CompoundTag tileTag = (CompoundTag) tile;
                Map<String, Tag> tileTagData = tileTag.getValue();
                
                StringTag entityTag = (StringTag) tileTagData.get("id");
                if (entityTag.getValue().equalsIgnoreCase("Chest")) {
                  IntTag xTag = (IntTag) tileTagData.get("x");
                  IntTag yTag = (IntTag) tileTagData.get("y");
                  IntTag zTag = (IntTag) tileTagData.get("z");
                  
                  int x = xTag.getValue();
                  int y = yTag.getValue();
                  int z = zTag.getValue();
                  
                  chestLocations.add(new ChestLocation(x, y, z));
                }
              }
            }
            else {
              throw new UnsupportedOperationException("Not a compound tag!");
            }
          }
        }
      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        try {
          if (nbtStream != null) nbtStream.close();
        } catch(IOException exc) {
          System.err.println(exc);
        }
      }
      
      counter ++;
      
      if (counter % 10 == 0) {
        System.err.println(counter / (double) regionFiles.length * 100 + " %");
      }
    }
  
    return chestLocations;
  }

  public static int byteArrayToInt(byte[] someBytes) {
    return ByteBuffer.wrap(someBytes).getInt();
  }

}
