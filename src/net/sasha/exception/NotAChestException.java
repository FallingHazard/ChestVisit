package net.sasha.exception;

public class NotAChestException extends Exception {
  private static final long serialVersionUID = -2437203332688542055L;

  public NotAChestException() {
    super();
  }

  public NotAChestException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
    super(arg0, arg1, arg2, arg3);
  }

  public NotAChestException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

  public NotAChestException(String arg0) {
    super(arg0);
  }

  public NotAChestException(Throwable arg0) {
    super(arg0);
  }

}
