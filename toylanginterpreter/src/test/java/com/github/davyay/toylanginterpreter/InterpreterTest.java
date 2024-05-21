package com.github.davyay.toylanginterpreter;

import java.util.List;

public class InterpreterTest {
    private static final Interpreter interpreter = new Interpreter();

    public static void main(String[] args) {
        testInterpreter("x = 001;", "error", "Invalid number format with leading zeros at position 4");
        testInterpreter("x_2 = 0;", "x_2 = 0", null);
        testInterpreter("x = 0\ny = x;\nz = ---(x+y);", "error", "Expect ';'. Found: y");
        testInterpreter("x = 1;\ny = 2;\nz = ---(x+y)*(x+-y);", "x = 1\ny = 2\nz = 3", null);
        testInterpreter("a = 5; b = a * 3; c = b - a + 2;", "a = 5\nb = 15\nc = 12", null);
        testInterpreter("x = 10; y = (x*2) / 4 + 3;", "x = 10\ny = 8", null);
        testInterpreter("z = 5 / 0;", "error", "Division by zero");
        testInterpreter("a = 5; b = c;", "error", "Variable 'c' not initialized");
        testInterpreter("x 10;", "error", "Expect '='. Found: 10");
        testInterpreter("x = (1 + (2 * (3 - 4)));", "x = -1", null);
        testInterpreter("x = (y + 1);", "error", "Uninitialized variable 'y'");
        testInterpreter("x = 1; y = z + 1; a = 2; b = a + c;", "error", "Uninitialized variable 'z'");
        testInterpreter("x = -1; y = +2; z = x + y;", "x = -1\ny = 2\nz = 1", null);
        testInterpreter("x = 5; x = 10;", "x = 10", null);
        testInterpreter("a = 2; b = (a + 3) * 4; c = b - 2;", "a = 2\nb = 20\nc = 18", null);
    }

    private static void testInterpreter(String input, String expectedOutput, String expectedError) {
        System.out.println("Testing input: " + input);
        try {
            Tokenizer tokenizer = new Tokenizer(input);
            List<Token> tokens = tokenizer.getTokens();
    
            Parser parser = new Parser(tokens);
            List<Stmt> statements = parser.parse();
    
            interpreter.interpret(statements);
            if (expectedOutput.equals("error")) {
                System.out.println("Expected: " + expectedError );
            } else {
                System.out.println("Output matched: " + expectedOutput);
            }
            System.out.println();
        } catch (Exception e) {
            if (expectedOutput.equals("error")) {
                System.out.println("Expected: " + expectedError + "\n" + e.getMessage());
            } else {
                System.out.println("Unexpected error: " + e.getMessage());
            }
            System.out.println();
        }
    }
}