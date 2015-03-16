import java.util.Stack;

/**
 * A class to evaluate postfix expressions.
 *
 * @author sahil
 */
public class Expression {

  /**
   * Evaluates a post fix expression
   * @param expression
   * @return String representation of the number
   */
  public static String evaluate(String expression) {
    Stack<Double> stack = new Stack<Double>();
    String[] elements = expression.split(" ");

    RuntimeException invalidExpression = new RuntimeException("Invalid expression");

    for (String element : elements) {
      if (element.replaceAll("[.]", "").matches("\\d+")) {
        // Push a number on the stack
        stack.push(Double.parseDouble(element));
      } else {
        if (stack.isEmpty()) throw invalidExpression;
        Double value1 = stack.pop();
        Double value2 = stack.pop();

        switch (element) {
        case "+":
          stack.push(value2 + value1);
          break;
        case "*":
          stack.push(value2 * value1);
          break;
        case "/":
          stack.push(value2 / value1);
          break;
        case "-":
          stack.push(value2 - value1);
          break;
        default:
          throw invalidExpression;
        }
      }
    }
    if (stack.isEmpty()) throw invalidExpression;
    return stack.pop().toString();
  }
}
