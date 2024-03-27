package ict.ihu.gr.myapplication;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ShuntingYardCalculator {

    private static final Map<String, Integer> precedence = new HashMap<>();

    static {
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);  // Asterisk (*) treated as an operator
        precedence.put("/", 2);
    }

    public double evaluate(String expression) throws NumberFormatException, IllegalArgumentException {
        Log.d("ShuntingYard", "entered evaluate  ");
        List<String> tokens = tokenize(expression);
        Stack<String> inputStack = new Stack<>();
        Stack<String> outputStack = new Stack<>();
        Log.d("ShuntingYard", "Stack declaration and stack initialisation ");
        for (int i = 0; i < tokens.size(); i++) {
            Log.d("ShuntingYard", "entered loop for " + i);
            String token = tokens.get(i);

            if (isNumeric(token)) {
                Log.d("ShuntingYard", "isNumeric(token):" + isNumeric(token));
                outputStack.push(token);
                Log.d("ShuntingYard","evaluate if is numeric: " + token );

            } else
//                if (isOperator(token)) {
            {
                Log.d("ShuntingYard", "Operator: " + token);
                Log.d("ShuntingYard", "Input Stack before: " + inputStack);
                Log.d("ShuntingYard", "Output Stack before: " + outputStack);
                while (!outputStack.isEmpty() && isOperator(outputStack.peek()) &&
                        precedence.get(token) <= precedence.get(outputStack.peek())) {
                    inputStack.push(outputStack.pop());
                }
                inputStack.push(token);
                Log.d("ShuntingYard", "Input Stack after: " + inputStack);
                Log.d("ShuntingYard", "Output Stack after: " + outputStack);
//            } else {
//                throw new IllegalArgumentException("Invalid token: " + token);
            }

        }

        while (!outputStack.isEmpty()) {
            Log.d("ShuntingYard", "while outputstack is empty " + outputStack.isEmpty());
            inputStack.push(outputStack.pop());
        }

        try {
            Log.d("ShuntingYard", "try evaluatePRN " + inputStack);
            return evaluateRPN(inputStack);  // Call RPN evaluation method
        } catch (InvalidNumberFormatException e) {
            // Handle invalid number format
            return Double.NaN;  // Or any other value to indicate error
        }
    }


        //return evaluateRPN(inputStack);


    private static List<String> tokenize(String expression) {
        Log.d("ShuntingYard", "entered tokenize  " + expression);
        List<String> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();
        for (char c : expression.toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                currentToken.append(c);
            } else if (currentToken.length() > 0) {
                tokens.add(currentToken.toString());
                currentToken.setLength(0);
            }
            if (isOperator(String.valueOf(c))) {  // Convert char to String
                tokens.add(String.valueOf(c));
            }
        }
        if (currentToken.length() > 0) {
            tokens.add(currentToken.toString());
        }
        Log.d("ShuntingYard", "exited tokenize  " + tokens);
        return tokens;
    }

    private static boolean isNumeric(String token) {
        Log.d("ShuntingYard", "entered isnumeric  " + token);

        if (isOperator(token)) { // Check for operators before numeric parsing
            Log.d("ShuntingYard", "isnumeric operator token: " + isOperator(token));
            return false;
        }
        if (token.isEmpty()) {
            return false; // Empty string is not numeric
        }

        // Handle leading/trailing whitespace (optional)
        token = token.trim();
        Log.d("ShuntingYard", "isnumeric trimmed token " + token);
        // Handle leading plus/minus signs (optional)
        if (token.charAt(0) == '+' || token.charAt(0) == '-') {
            Log.d("ShuntingYard", "isnumeric  " + (token.length() > 1 && !isNumeric(token.substring(1))));
            return token.length() > 1 && !isNumeric(token.substring(1)); // Allow leading signs only if followed by a valid number
        }
        Log.d("ShuntingYard", "exited isnumeric  " + !Double.isNaN(Double.parseDouble(token)));
        try {
            // Check for non-NaN numbers using Double.parseDouble
            return !Double.isNaN(Double.parseDouble(token));
        } catch (NumberFormatException e) {
            // Throw custom exception for better error handling
            throw new InvalidNumberFormatException("Invalid number format: " + token);
        }
    }

    private static boolean isOperator(String token) {
        token = token.trim();

        Log.d("ShuntingYard", "entered isoperator  " + token);
        Log.d("ShuntingYard", "exited isoperator  " + precedence.containsKey(token));
        return precedence.containsKey(token);
    }

    public double evaluateRPN(Stack<String> stack) {
        double value1 = 0, value2 = 0, result = 0;
        int i=0;
        while (!stack.isEmpty()) {
            Log.d("ShuntingYard", "while stack is empty " + stack.isEmpty());
            String token = stack.pop();

            Log.d("ShuntingYard", "while PRN token " + token);
            if (isNumeric(token)) {
                value2 = value1;
                try {
                    value1 = Double.parseDouble(token); // Parse numeric tokens
                } catch (NumberFormatException e) {
                    // Handle invalid number format (e.g., throw a custom exception)
                    throw new InvalidNumberFormatException("Invalid number format: " + token);
                }
            } else {
                Log.d("ShuntingYard", "while PRN if else token " + token);
//                value2 = evaluateRPN(stack); // Get the second operand (double)
//                value1 = Double.parseDouble(stack.pop()); // Parse the first operand (double)
                Log.d("ShuntingYard", "while stack " + stack + " while value1 " + value1 + " while value2 " + value2 + " while iteration " + i + " token " + token);
                switch (token) {
                    case "+":
                        value1 += value2;
                        break;
                    case "-":
                        value1 -= value2;
                        break;
                    case "*":
                        result = value1 * value2;
                        break;
                    case "/":
                        if (value2 == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        value1 /= value2;
                        break;
                }
                stack.push(String.valueOf(result));
                Log.d("ShuntingYard", "stack result " + stack);
                break;
            }
            // No conversion needed, push result back as String
            //infinite loop
            Log.d("ShuntingYard", "while stack " + stack + " while value1 " + value1 + " while value2 " + value2 + " while iteration " + i);
            i++;
        }
        return Double.parseDouble(stack.pop()); // Final result (double)
    }

    public Stack<String> convertToRPN(String expression) {
        Stack<String> operatorStack = new Stack<>();
        Stack<String> operandStack = new Stack<>();

        // Iterate through each character in the expression
        for (char token : expression.toCharArray()) {
            String tokenString = String.valueOf(token); // Convert char to String

            if (isNumeric(tokenString)) {
                operandStack.push(tokenString); // Push numeric tokens as Strings
            } else if (isOperator(tokenString)) {
                while (!operatorStack.isEmpty() &&
                        hasHigherPrecedence(operatorStack.peek(), tokenString)) {
                    operandStack.push(operatorStack.pop());
                }
                operatorStack.push(tokenString);
            } else if (tokenString.equals("(")) {
                operatorStack.push(tokenString);
            } else if (tokenString.equals(")")) {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    operandStack.push(operatorStack.pop());
                }
                // Handle missing opening parenthesis error (if needed)
                if (operatorStack.isEmpty() || !operatorStack.peek().equals("(")) {
                    throw new MissingOpeningParenthesisException("Missing opening parenthesis");
                }
                operatorStack.pop(); // Remove the opening parenthesis
            } else {
                // Handle unexpected characters (optional)
                throw new InvalidCharacterException("Invalid character: " + tokenString);
            }
        }

        // Process remaining operators in the stack
        while (!operatorStack.isEmpty()) {
            operandStack.push(operatorStack.pop());
        }

        return operandStack;
    }

    private boolean hasHigherPrecedence(String op1, String op2) {
        // Define precedence levels (higher value indicates higher precedence)
        int precedence1 = (op1.equals("+") || op1.equals("-")) ? 1 : 2;
        int precedence2 = (op2.equals("+") || op2.equals("-")) ? 1 : 2;
        return precedence1 >= precedence2;
    }


}
class InsufficientOperandsException extends RuntimeException {
    public InsufficientOperandsException(String message) {
        super(message);
    }
}

