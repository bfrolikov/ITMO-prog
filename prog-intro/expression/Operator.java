package expression;

import java.util.Map;

public enum Operator {
    ADD(true, "+"), SUBTRACT(false, "-"), MULTIPLY(true, "*"),
    DIVIDE(false, "/"), UNARY_MINUS(false, "-"), AND(true, "&"),
    XOR(true, "^"), OR(true, "|"), NOT(false, "~"), COUNT(false, "count"),
    NONE(false, ""), ABS(false, "abs"), SQRT(false, "sqrt"),
    MIN(true, "min"), MAX(true, "max");

    private final boolean associative;
    private final String representation;
    private static final Map<Operator, Operator> alterMap = Map.of(
            SUBTRACT, ADD,
            MULTIPLY, DIVIDE,
            DIVIDE, MULTIPLY,
            MAX, MIN,
            MIN, MAX
    ); //operators in each pair have the same priority

    Operator(final boolean associative, final String representation) {
        this.associative = associative;
        this.representation = representation;
    }

    public boolean alters(Operator that) {
        final Operator temp = alterMap.get(this);
        return temp != null && temp.equals(that);
    }

    public boolean isAssociative() {
        return associative;
    }

    @Override
    public String toString() {
        return representation;
    }

    public Priority getOperatorPriority() {
        switch (this) {
            case MIN:
            case MAX:
                return Priority.FIRST;
            case OR:
                return Priority.SECOND;
            case XOR:
                return Priority.THIRD;
            case AND:
                return Priority.FOURTH;
            case ADD:
            case SUBTRACT:
                return Priority.FIFTH;
            case DIVIDE:
            case MULTIPLY:
                return Priority.SIXTH;
            case UNARY_MINUS:
            case NOT:
            case COUNT:
            case ABS:
            case SQRT:
                return Priority.UNARY;
            default:
                return Priority.N_A;
        }
    }

    public enum Priority {
        FIRST, SECOND, THIRD, FOURTH, FIFTH, SIXTH, UNARY, N_A;
    }

}
