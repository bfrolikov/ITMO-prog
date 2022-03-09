package expression.generic.evaluators;

import expression.exceptions.ZeroDivisionException;

public class UncheckedIntEvaluator extends EvaluatorWithParser<Integer> {
    @Override
    public Integer add(final Integer first, final Integer second) {
        return first + second;
    }

    @Override
    public Integer subtract(final Integer first, final Integer second) {
        return first - second;
    }

    @Override
    public Integer multiply(final Integer first, final Integer second) {
        return first * second;
    }

    @Override
    public Integer divide(final Integer first, final Integer second) {
        if (second == 0) {
            throw new ZeroDivisionException("Division by zero happened during evaluation");
        }
        return first / second;
    }

    @Override
    public Integer mod(final Integer first, final Integer second) {
        if (second == 0) {
            throw new ZeroDivisionException("Modulo by zero happened during evaluation");
        }
        return first % second;
    }

    @Override
    public Integer negate(final Integer first) {
        return -first;
    }

    @Override
    public Integer abs(final Integer first) {
        return Math.abs(first);
    }

    @Override
    public Integer square(final Integer first) {
        return first * first;
    }

    @Override
    public Integer fromString(final String s) {
        return Integer.parseInt(s);
    }

    @Override
    public Integer fromInt(final int i) {
        return i;
    }
}
