package scope;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.logging.Logger;

/**
 * Lookup method gives full flexibility. We can choose where
 * to create a new bean and when to use an old prototype bean.
 */
@Component
public abstract class FirstBeanLookup extends FirstBean {
  @PostConstruct
  public void init() {
    this.b = createSecondBean();
  }

  @Override
  public String getMsg() {
    this.a = createSecondBean();
    return super.getMsg();
  }

  @Lookup("beanBPrototype")
  public abstract SecondBean createSecondBean();
}
