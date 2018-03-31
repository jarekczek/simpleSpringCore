package scope;

import java.util.logging.Logger;

public class ClassWithId {
  private static int counter = 0;
  protected Logger log;

  public String getId() {
    return id;
  }

  private String id;

  public ClassWithId() {
    log = Logger.getLogger("bs");
    synchronized(ClassWithId.class) {
      id = Character.toString((char) ('A' + counter));
      counter++;
      log.fine("created " + this.getClass().getSimpleName() + "-" + id);
    }
  }
}
