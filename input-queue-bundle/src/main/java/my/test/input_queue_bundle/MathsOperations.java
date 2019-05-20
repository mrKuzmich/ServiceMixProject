package my.test.input_queue_bundle;

import my.test.calc_bundle.calculator.FactorialCalculator;

import java.util.logging.Logger;

public class MathsOperations {
  private Logger LOGGER = Logger.getLogger(MathsOperations.class.getName());

  private FactorialCalculator calculator;

  public Object Factorial(Object request) {
    try {
      LOGGER.info("Incoming request: " + request.toString());
      final Integer n = checkRequest(request);
      final String response = calculator.apply(n).toString();
      LOGGER.info("Outgoing response: " + response);
      return response;
    } catch (Exception e) {
      return e;
    }
  }

  private Integer checkRequest(Object request) {
    if (request != null)
      try {
        return Integer.valueOf(request.toString());
      } catch (NumberFormatException e) {
        throw new IllegalArgumentException("Request format error");
      }
    else
      throw new IllegalArgumentException("Empty request");
  }

  public FactorialCalculator getCalculator() {
    return calculator;
  }

  public void setCalculator(FactorialCalculator calculator) {
    this.calculator = calculator;
  }
}
