package scope;

import java.util.logging.Logger;

public class FirstBean extends ClassWithId {
  SecondBean a;
  SecondBean b;

  public FirstBean() {}

  public FirstBean(SecondBean a, SecondBean b) {
    this.a = a;
    this.b = b;
  }

  public String getMsg() {
    log.fine("getMsg for class " + this.getClass().getSimpleName());
    String msg = getId() + "-";
    msg += a.getMsg() + a.getMsg() + "-";
    msg += b.getMsg() + b.getMsg();
    return msg;
  }
}
