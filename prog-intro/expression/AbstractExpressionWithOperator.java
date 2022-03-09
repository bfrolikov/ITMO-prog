package expression;

public abstract class AbstractExpressionWithOperator implements PrioritizedExpression {

    @Override
    public Operator.Priority getPriority() {
        return getOperator().getOperatorPriority();
    }
}
