package my.test.input_queue_bundle;

import my.test.calc_bundle.calculator.FactorialCalculator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.logging.Logger;

public class FactorialCalculatorFactory implements BundleActivator {
  private static final Logger LOGGER = Logger.getLogger(FactorialCalculatorFactory.class.getName());
  private static BundleContext bundleContext;
  private static ServiceReference serviceReference;
  private volatile static FactorialCalculator INSTANCE = null;

  public static FactorialCalculator getInstance() {
    if (INSTANCE == null)
      synchronized (FactorialCalculatorFactory.class) {
        if (bundleContext == null) throw new NullPointerException("Undefined Bundle Context.");
        serviceReference = bundleContext.getServiceReference(FactorialCalculator.class.getName());
        if (serviceReference != null)
          INSTANCE = (FactorialCalculator) bundleContext.getService(serviceReference);
        if (INSTANCE == null)
          throw new RuntimeException("FactorialCalculator service not registered.");
        LOGGER.info("Received instance of " + INSTANCE.getClass().getName());
      }
    return INSTANCE;
  }

  @Override
  public void start(BundleContext context) throws Exception {
    bundleContext = context;
  }

  @Override
  public void stop(BundleContext context) throws Exception {
    synchronized (FactorialCalculatorFactory.class) {
      if (serviceReference != null)
        context.ungetService(serviceReference);
      bundleContext = null;
      INSTANCE = null;
    }
  }

  public static BundleContext getBundleContext() {
    return bundleContext;
  }

  public static void setBundleContext(BundleContext bundleContext) {
    FactorialCalculatorFactory.bundleContext = bundleContext;
  }
}
