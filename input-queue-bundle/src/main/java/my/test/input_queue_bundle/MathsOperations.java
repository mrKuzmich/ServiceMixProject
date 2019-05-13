package my.test.input_queue_bundle;

import my.test.calc_bundle.calculator.FactorialCalculator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Objects;

public class MathsOperations {
  private Log LOGGER = LogFactory.getLog(MathsOperations.class);

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
