package expression.exceptions;

public class ZeroDivisionException extends EvaluationException {
    public ZeroDivisionException(final String message) {
        super(message);
    }
}
