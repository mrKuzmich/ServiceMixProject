package my.test.calc_bundle.calculator;

import java.math.BigInteger;

public class SimpleFactorialCalculator implements FactorialCalculator {

    @Override
    public BigInteger apply(Integer n) {
        if (n < 0) throw new ArithmeticException("Function undefined at interval n < 0");
        return n <= 1 ?
                BigInteger.ONE :
                BigInteger.valueOf(n).multiply(apply(n - 1));
    }

}
