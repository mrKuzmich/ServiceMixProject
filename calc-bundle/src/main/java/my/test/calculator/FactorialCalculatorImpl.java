package my.test.calculator;

import java.math.BigInteger;

public class FactorialCalculatorImpl implements FactorialCalculator {

    @Override
    public BigInteger apply(Integer n) {
        if (n < 0) throw new ArithmeticException("Function undefined at interval n < 0");
        return n <= 1 ?
                BigInteger.ONE :
                BigInteger.valueOf(n).multiply(apply(n - 1));
    }

}
