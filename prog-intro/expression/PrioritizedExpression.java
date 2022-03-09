package expression;

public interface PrioritizedExpression extends Expression, DoubleExpression, TripleExpression {
    Operator.Priority getPriority();

    Operator getOperator();
}
