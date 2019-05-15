package my.test.input_queue_bundle;

import my.test.calc_bundle.calculator.FactorialCalculator;

import java.util.Objects;
import java.util.logging.Logger;

public class MathsOperations {
  private Logger LOGGER = Logger.getLogger(MathsOperations.class.getName());

  private final FactorialCalculator calculator;

  public MathsOperations(FactorialCalculator calculator) {
    this.calculator = calculator;
  }

  public Object Factorial(Object request) {
    try {
      LOGGER.info("Incoming request: " + request.toString());
      Integer n = Integer.valueOf(Objects.requireNonNull(request, "Empty request").toString());
      final String response = calculator.apply(n).toString();
      LOGGER.info("Outgoing response: " + response);
      return response;
    } catch (Exception e) {
      return "Calculate Factorial exception: " + e.getClass().getCanonicalName() + ": " + e.getMessage();
    }
  }
}
