package my.test.calc_bundle.calculator;

import java.math.BigInteger;

@FunctionalInterface
public interface FactorialCalculator {

  BigInteger apply(Integer integer);

}
