package expression.generic;

import expression.generic.operations.TripleExpression;
import expression.exceptions.ParseException;
import expression.generic.evaluators.*;

import java.util.Map;

public class GenericTabulator implements Tabulator {
    private static final Map<String, EvaluatorWithParser<?>> EVALUATORS = Map.of(
            "i", new IntegerEvaluator(),
            "d", new DoubleEvaluator(),
            "bi", new BigIntegerEvaluator(),
            "u", new UncheckedIntEvaluator(),
            "l", new LongEvaluator(),
            "s", new ShortEvaluator()
    );

    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParseException {
        return tabulateImpl(EVALUATORS.get(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private <T> Object[][][] tabulateImpl(EvaluatorWithParser<T> evaluator, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws ParseException {
        final TripleExpression<T> exprToEvaluate = evaluator.parse(expression);
        final Object[][][] arr = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                for (int k = 0; k < arr[i][j].length; k++) {
                    try {
                        arr[i][j][k] = exprToEvaluate.evaluate(evaluator.fromInt(x1 + i), evaluator.fromInt(y1 + j), evaluator.fromInt(z1 + k));
                    } catch (ArithmeticException ignored) {

                    }
                }
            }
        }
        return arr;
    }

    public static void main(String[] args) {
        try {
            if (args.length < 2) {
                throw new IllegalArgumentException("Expected 2 arguments, got " + args.length);
            }
            String mode;
            if (args[0] == null || args[0].length() < 2 || args[0].charAt(0) != '-' || !EVALUATORS.containsKey(mode = args[0].substring(1))) {
                throw new IllegalArgumentException("Unknown mode: " + args[0]);
            }
            if (args[1] == null) {
                throw new IllegalArgumentException("Expression can't be null");
            }
            int x1 = -2, y1 = -2, z1 = -2;
            int x2 = 2, y2 = 2, z2 = 2;
            Object[][][] res = new GenericTabulator().tabulate(mode, args[1], x1, x2, y1, y2, z1, z2);
            for (int i = 0; i < res.length; i++) {
                for (int j = 0; j < res[i].length; j++) {
                    for (int k = 0; k < res[i][j].length; k++) {
                        System.out.printf("x: %d, y: %d, z:%d, value: %s%n", x1 + i, y1 + j, z1 + k, res[i][j][k]);
                    }
                }
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
