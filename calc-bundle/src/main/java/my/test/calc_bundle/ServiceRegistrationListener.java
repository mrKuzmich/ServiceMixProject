package my.test.calc_bundle;

import my.test.calc_bundle.calculator.FactorialCalculator;
import org.osgi.framework.ServiceFactory;

import java.util.Map;
import java.util.logging.Logger;

public class ServiceRegistrationListener {
  private static final Logger LOGGER = Logger.getLogger(FactorialCalculator.class.getName());

  public void register(ServiceFactory serviceFactory, Map map) {
    LOGGER.info("Factorial calculator register");
  }

  public void unregister(ServiceFactory serviceFactory, Map map) {
    LOGGER.info("Factorial calculator unregister");
  }
}
