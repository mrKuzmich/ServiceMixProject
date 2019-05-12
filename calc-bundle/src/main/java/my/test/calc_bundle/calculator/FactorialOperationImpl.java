package my.test.calc_bundle.calculator;

import java.math.BigInteger;

public class FactorialOperationImpl implements FactorialOperation {

  private final FactorialCalculator calculator;

  public FactorialOperationImpl(FactorialCalculator calculator) {
    this.calculator = calculator;
  }

  @Override
  public BigInteger calc(int n) {
    return calculator.apply(n);
  }
}
