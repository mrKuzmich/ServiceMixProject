package my.test.input_queue_bundle;

import my.test.calc_bundle.calculator.FactorialCalculator;

import java.util.Objects;
import java.util.logging.Logger;

public class MathsOperations {
  private Logger LOGGER = Logger.getLogger(MathsOperations.class.getName());

  private static FactorialCalculator calculator;

  public Object Factorial(Object request) {
    try {
      if (calculator == null) throw new NullPointerException("Undefined calculator");
      LOGGER.info("Incoming request: " + request.toString());
      Integer n = Integer.valueOf(Objects.requireNonNull(request, "Empty request").toString());
      final String response = calculator.apply(n).toString();
      LOGGER.info("Outgoing response: " + response);
      return response;
    } catch (Exception e) {
      return "Calculate Factorial exception: " + e.getClass().getCanonicalName() + ": " + e.getMessage();
    }
  }

  public static FactorialCalculator getCalculator() {
    return calculator;
  }

  public static void setCalculator(FactorialCalculator factorialCalculator) {
    calculator = factorialCalculator;
  }
}
