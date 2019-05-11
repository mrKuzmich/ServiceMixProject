package my.test.input_queue_bundle;

import my.test.calc_bundle.calculator.FactorialCalculatorImpl;

import java.util.Objects;

public class MathsOperations {
  public Object Factorial(Object request) {
    try {
      Integer n = Integer.valueOf(Objects.requireNonNull(request, "Empty request").toString());
      return new FactorialCalculatorImpl().apply(n).toString();
    } catch (Exception e) {
      return "Calculate Factorial exception: " + e.getClass().getCanonicalName() + ": " + e.getMessage();
    }
  }
}
