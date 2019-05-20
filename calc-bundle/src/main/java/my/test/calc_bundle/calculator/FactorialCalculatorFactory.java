package my.test.calc_bundle.calculator;

import org.osgi.framework.*;
import org.osgi.service.blueprint.container.BlueprintContainer;

import java.util.Objects;

public class FactorialCalculatorFactory implements ServiceFactory {
  private volatile FactorialCalculator calculator; // use singleton instance for all external bundles
  private final BundleContext context;

  public FactorialCalculatorFactory(BundleContext context) {
    this.context = Objects.requireNonNull(context);
  }

  @Override
  public Object getService(Bundle bundle, ServiceRegistration registration) {
    if (calculator == null)
      createCalculator();
    return calculator;
  }

  @Override
  public void ungetService(Bundle bundle, ServiceRegistration registration, Object service) {
    // nothing
  }

  private synchronized void createCalculator() {
    if (calculator == null) {
      // find instance in blueprint container
      final BlueprintContainer blueprintContainer = getBlueprintContainer(context);
      if (blueprintContainer != null)
        calculator = (FactorialCalculator) blueprintContainer.getComponentInstance("calculatorInst");
    }
    // if not found, create default
    if (calculator == null)
      calculator = new SimpleFactorialCalculator();
  }

  private BlueprintContainer getBlueprintContainer(BundleContext context) {
    try {
      ServiceReference[] serviceReferences = context.getServiceReferences(BlueprintContainer.class.getName(),
              "(osgi.blueprint.container.symbolicname=" + context.getBundle().getSymbolicName() + ")");
      if (serviceReferences.length == 0) {
        return null;
      }
      final ServiceReference reference = serviceReferences[0];
      return (BlueprintContainer) context.getService(reference);
    } catch (InvalidSyntaxException e) {
      return null;
    }
  }

}
