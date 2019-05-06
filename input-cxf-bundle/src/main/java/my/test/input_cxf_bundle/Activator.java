package my.test.input_cxf_bundle;

import my.test.calc_bundle.calculator.FactorialCalculator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {
    private static FactorialCalculator calculator;
    private ServiceReference<FactorialCalculator> serviceReference;

    @Override
    public void start(BundleContext context) throws Exception {
        serviceReference = context.getServiceReference(FactorialCalculator.class);
        if (serviceReference != null)
            calculator = context.getService(serviceReference);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (serviceReference != null)
            context.ungetService(serviceReference);
    }

    public static FactorialCalculator getCalculator() {
        return calculator;
    }
}
