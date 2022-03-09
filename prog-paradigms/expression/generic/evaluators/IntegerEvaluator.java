package expression.generic.evaluators;

import expression.exceptions.OverflowException;
import expression.exceptions.ZeroDivisionException;

public class IntegerEvaluator extends EvaluatorWithParser<Integer> {

    @Override
    public Integer add(final Integer first, final Integer second) {
        if (checkAddOverflow(first, second)) {
            throw new OverflowException("Overflow happened during addition");
        }
        return first + second;
    }

    @Override
    public Integer subtract(final Integer first, final Integer second) {
        if (checkSubtractOverflow(first, second)) {
            throw new OverflowException("Overflow happened during subtraction");
        }
        return first - second;
    }

    @Override
    public Integer multiply(final Integer first, final Integer second) {
        if (checkMultiplyOverflow(first, second)) {
            throw new OverflowException("Overflow happened during multiplication");
        }
        return first * second;
    }

    @Override
    public Integer divide(final Integer first, final Integer second) {
        if (second == 0) {
            throw new ZeroDivisionException("Division by zero happened during evaluation");
        }
        if (checkDivideOverflow(first, second)) {
            throw new OverflowException("Overflow happened during division");
        }
        return first / second;
    }

    @Override
    public Integer mod(final Integer first, final Integer second) {
        if (second == 0) {
            throw new ZeroDivisionException("Modulo by 0 happened during evaluation");
        }
        return first % second;
    }

    @Override
    public Integer negate(final Integer first) {
        if (checkNegateOverflow(first)) {
            throw new OverflowException("Overflow happened during negation");
        }
        return -first;
    }

    @Override
    public Integer abs(final Integer first) {
        if (checkAbsOverflow(first)) {
            throw new OverflowException("Overflow happened during division");
        }
        return Math.abs(first);
    }

    @Override
    public Integer square(final Integer first) {
        if (checkMultiplyOverflow(first, first)) {
            throw new OverflowException("Overflow happened during multiplication");
        }
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

    private boolean checkAddOverflow(final Integer first, final Integer second) {
        if (first > 0 && second > 0) {
            return first > Integer.MAX_VALUE - second;
        } else if (first < 0 && second < 0) {
            return first < Integer.MIN_VALUE - second;
        }
        return false;
    }

    private boolean checkSubtractOverflow(final Integer first, final Integer second) {
        if (first < 0 && second > 0) {
            return first < Integer.MIN_VALUE + second;
        } else {
            return first >= 0 && second < 0 && first > Integer.MAX_VALUE + second;
        }
    }

    private boolean checkMultiplyOverflow(final Integer first, final Integer second) {
        if (first == 0 || second == 0)
            return false;
        if (first > 0 && second > 0) {
            return first > Integer.MAX_VALUE / second;
        } else if (first < 0 && second < 0) {
            return first < Integer.MAX_VALUE / second;
        } else if (first > 0) {
            return second < Integer.MIN_VALUE / first;
        } else {
            return first < Integer.MIN_VALUE / second;
        }
    }

    private boolean checkDivideOverflow(final Integer first, final Integer second) {
        return first == Integer.MIN_VALUE && second == -1;
    }

    private boolean checkNegateOverflow(final Integer first) {
        return first == Integer.MIN_VALUE;
    }

    private boolean checkAbsOverflow(final Integer first) {
        return first == Integer.MIN_VALUE;
    }
}
