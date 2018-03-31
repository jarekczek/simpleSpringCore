package scope;

import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class Tests
{
  Logger logger;

  @Autowired
  private ApplicationContext applicationContext;

  public Tests()
  {
    logger = Logger.getLogger("bs");
  }

  private Character[] runTestForBean(String beanName) {
    System.out.println("running test for " + beanName);
    FirstBean a = (FirstBean)this.applicationContext.getBean(beanName);
    String msg = a.getMsg();
    System.out.println(msg);
    return msg.subSequence(0, msg.length()).chars()
      .mapToObj(i -> ((char) i))
      .toArray(Character[]::new);
  }

  @Test
  public void testSingletonSingleton()
  {
    Character[] msg = runTestForBean("beanAsingletonSingleton");
    Character firstRunA = msg[0];
    Character firstRunB = msg[2];
    Assert.assertEquals(msg[2], msg[3]);
    Assert.assertEquals(msg[3], msg[5]);
    Assert.assertEquals(msg[5], msg[6]);
    msg = runTestForBean("beanAsingletonSingleton");
    Assert.assertEquals(msg[2], msg[3]);
    Assert.assertEquals(msg[3], msg[5]);
    Assert.assertEquals(msg[5], msg[6]);
    Assert.assertEquals(firstRunA, msg[0]);
    Assert.assertEquals(firstRunB, msg[2]);
  }

  @Test
  public void testSingletonPrototype()
  {
    Character[] msg = runTestForBean("beanAsingletonPrototype");
    Character firstRunA = msg[0];
    Assert.assertEquals(msg[2], msg[3]);
    Assert.assertEquals(msg[5], msg[6]);
    Assert.assertNotEquals(msg[3], msg[5]);
    msg = runTestForBean("beanAsingletonPrototype");
    Assert.assertEquals(firstRunA, msg[0]);
  }

  @Test
  public void testSingletonProxyPrototype()
  {
    Character[] msg = runTestForBean("beanAsingletonProxyPrototype");
    Assert.assertNotEquals(msg[2], msg[3]);
    Assert.assertNotEquals(msg[5], msg[6]);
    Assert.assertNotEquals(msg[3], msg[5]);
    Character firstRunA = msg[0];

    msg = runTestForBean("beanAsingletonProxyPrototype");
    Assert.assertEquals(firstRunA, msg[0]);
  }

  @Test
  public void testSingletonLookupPrototype()
  {
    Character[] msg = runTestForBean("firstBeanLookup");
    Assert.assertEquals(msg[2], msg[3]);
    Assert.assertEquals(msg[5], msg[6]);
    Assert.assertNotEquals(msg[3], msg[5]);
  }

  @Configuration
  @ComponentScan
  public static class Conf {
    @Bean @Scope("singleton")
    public SecondBean beanBSingleton() { return new SecondBean(); }

    @Bean @Scope("prototype")
    public SecondBean beanBPrototype() { return new SecondBean(); }

    @Bean @Scope(scopeName = "prototype", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SecondBean beanBProxyPrototype() { return new SecondBean(); }

    @Bean
    public FirstBean beanAsingletonSingleton() {
      return new FirstBean(beanBSingleton(), beanBSingleton());
    }

    @Bean
    public FirstBean beanAsingletonPrototype() {
      return new FirstBean(beanBPrototype(), beanBPrototype());
    }

    @Bean
    public FirstBean beanAsingletonProxyPrototype() {
      return new FirstBean(beanBProxyPrototype(), beanBProxyPrototype());
    }
  }
}
