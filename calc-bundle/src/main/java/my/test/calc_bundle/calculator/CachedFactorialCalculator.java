package my.test.calc_bundle.calculator;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CachedFactorialCalculator implements FactorialCalculator {

  // TODO: this is a bad cache, need to use replacement policies
  private static Map<Integer, BigInteger> cache = new ConcurrentHashMap<>();

  @Override
  public BigInteger apply(Integer n) {
      return cache.computeIfAbsent(n, nn -> new SimpleFactorialCalculator().apply(nn));
  }
}
