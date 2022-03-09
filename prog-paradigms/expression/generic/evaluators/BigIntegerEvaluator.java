package expression.generic.evaluators;

import expression.exceptions.ZeroDivisionException;

import java.math.BigInteger;

public class BigIntegerEvaluator extends EvaluatorWithParser<BigInteger> {
    @Override
    public BigInteger add(final BigInteger first, final BigInteger second) {
        return first.add(second);
    }

    @Override
    public BigInteger subtract(final BigInteger first, final BigInteger second) {
        return first.subtract(second);
    }

    @Override
    public BigInteger multiply(final BigInteger first, final BigInteger second) {
        return first.multiply(second);
    }

    @Override
    public BigInteger divide(final BigInteger first, final BigInteger second) {
        if (second.compareTo(BigInteger.ZERO) == 0) {
            throw new ZeroDivisionException("Division by zero happened during evaluation");
        }
        return first.divide(second);
    }

    @Override
    public BigInteger mod(final BigInteger first, final BigInteger second) {
        if (second.compareTo(BigInteger.ZERO) == 0) {
            throw new ZeroDivisionException("Modulo by zero happened during evaluation");
        }
        return first.mod(second);
    }

    @Override
    public BigInteger negate(final BigInteger first) {
        return first.negate();
    }

    @Override
    public BigInteger abs(final BigInteger first) {
        return first.abs();
    }

    @Override
    public BigInteger square(final BigInteger first) {
        return first.multiply(first);
    }

    @Override
    public BigInteger fromString(final String s) {
        return new BigInteger(s);
    }

    @Override
    public BigInteger fromInt(final int i) {
        return BigInteger.valueOf(i);
    }
}
