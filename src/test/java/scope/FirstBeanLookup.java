package scope;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

@Component
public abstract class FirstBeanLookup extends FirstBean {
  @PostConstruct
  public void init() {
    Logger.getLogger("bs").info("initializacing FirstBeanLookup");
    this.a = createSecondBean();
    this.b = createSecondBean();
  }

  @Lookup("beanBPrototype")
  public abstract SecondBean createSecondBean();
}
