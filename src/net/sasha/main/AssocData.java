package net.sasha.main;

public class AssocData {
  private final byte[] internalArray;
  private int pointer;
  
  public AssocData(byte[] newInternalArray) {
    internalArray = newInternalArray;
    pointer = 0;
  }
  
  public Byte readValue() {
    if (pointer >= internalArray.length) {
      return null;
    }
    else {
      byte someValue = internalArray[pointer];
      
      pointer ++;
      return someValue;
    }
  }
  
  public boolean hasNext() {
    return pointer < internalArray.length;
  }
}
