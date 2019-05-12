package my.test.input_queue_bundle;

import my.test.calc_bundle.calculator.FactorialCalculator;

import java.util.Objects;

public class MathsOperations {

  private final FactorialCalculator calculator;

  public MathsOperations(FactorialCalculator calculator) {
    this.calculator = calculator;
  }

  public Object Factorial(Object request) {
    try {
      Integer n = Integer.valueOf(Objects.requireNonNull(request, "Empty request").toString());
      return calculator.apply(n).toString();
    } catch (Exception e) {
      return "Calculate Factorial exception: " + e.getClass().getCanonicalName() + ": " + e.getMessage();
    }
  }
}
