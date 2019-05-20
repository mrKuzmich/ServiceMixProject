package my.test.calc_bundle;

import my.test.calc_bundle.calculator.FactorialCalculator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.math.BigInteger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:META-INF/spring/test-context.xml"})
public class FactorialCalculatorTest extends Assert {

  @Resource(name = "simpleFactorialCalculator")
  private FactorialCalculator simpleCalculator;

  @Resource(name = "cachedFactorialCalculator")
  private FactorialCalculator cachedCalculator;

  @Test
  public void simpleFactorialTest() {
    assertEquals(BigInteger.ONE, simpleCalculator.apply(0));
    assertEquals(BigInteger.valueOf(3628800L), simpleCalculator.apply(10));
  }

  @Test
  public void cachedFactorialTest() {
    assertEquals(BigInteger.ONE, cachedCalculator.apply(0));
    assertEquals(BigInteger.valueOf(3628800L), cachedCalculator.apply(10));
  }

  @Test(expected = IllegalArgumentException.class)
  public void simpleFactorialTestException() {
    simpleCalculator.apply(-1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void cachedFactorialTestException() {
    cachedCalculator.apply(-1);
  }
}