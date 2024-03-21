package ict.ihu.gr.myapplication;

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
        List<String> tokens = tokenize(expression);
        Stack<String> inputStack = new Stack<>();
        Stack<String> outputStack = new Stack<>();

        for (String token : tokens) {
            if (isNumeric(token)) {
                outputStack.push(token);
            } else if (isOperator(token)) {
                while (!outputStack.isEmpty() && isOperator(outputStack.peek()) &&
                        (precedence.get(token) <= precedence.get(outputStack.peek()))) {
                    inputStack.push(outputStack.pop());
                }
                inputStack.push(token);
            } else {
                throw new IllegalArgumentException("Invalid token: " + token);
            }
        }

        while (!outputStack.isEmpty()) {
            inputStack.push(outputStack.pop());
        }

        try {
            return evaluateRPN(inputStack);  // Call RPN evaluation method
        } catch (InvalidNumberFormatException e) {
            // Handle invalid number format
            return Double.NaN;  // Or any other value to indicate error
        }
    }


        //return evaluateRPN(inputStack);


    private static List<String> tokenize(String expression) {
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
        return tokens;
    }

    private static boolean isNumeric(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            throw new InvalidNumberFormatException("Invalid number format: " + token);
        }
    }

    private static boolean isOperator(String token) {
        return precedence.containsKey(token);
    }

    private double evaluateRPN(Stack<String> stack) {
        double value1, value2;
        while (!stack.isEmpty()) {
            String token = stack.pop();
            if (isNumeric(token)) {
                value1 = Double.parseDouble(token);
            } else {
                value2 = evaluateRPN(stack);
                value1 = evaluateRPN(stack);
                switch (token) {
                    case "+":
                        value1 += value2;
                        break;
                    case "-":
                        value1 -= value2;
                        break;
                    case "*":
                        value1 *= value2;
                        break;
                    case "/":
                        if (value2 == 0) {
                            throw new ArithmeticException("Division by zero");
                        }
                        value1 /= value2;
                        break;
                }
            }
            stack.push(String.valueOf(value1)); // Push the result back
        }
        return Double.parseDouble(stack.pop()); // Final result
    }
}
