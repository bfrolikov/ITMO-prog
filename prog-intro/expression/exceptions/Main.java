import expression.TripleExpression;
import expression.exceptions.EvaluationException;
import expression.exceptions.ExpressionParser;
import expression.exceptions.ParseException;


public class Main {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();
        TripleExpression res;
        try {
            res = parser.parse("countx");
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            return;
        }
        for (int i = 0; i <= 10; i++) {
            System.out.print(i + "\t");
            try {
                System.out.println(res.evaluate(i, 0, 0));
            } catch (EvaluationException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
