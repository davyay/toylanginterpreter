package com.github.davyay.toylanginterpreter;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        Interpreter interpreter = new Interpreter();

        while (true) {
            System.out.println("Enter your code (end with an empty line, type 'exit' to quit):");
            StringBuilder inputBuilder = new StringBuilder();

            while (true) {
                String line = scanner.nextLine();
                if (line.isEmpty()) {
                    break;
                }
                if (line.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    return;
                }
                inputBuilder.append(line).append("\n");
            }

            String source = inputBuilder.toString().trim();
            if (source.isEmpty()) {
                continue;
            }

            try {
                Tokenizer tokenizer = new Tokenizer(source);
                List<Token> tokens = tokenizer.getTokens();

                // Print tokens for debugging
                System.out.println("Tokens:");
                for (Token token : tokens) {
                    System.out.println(token);
                }

                Parser parser = new Parser(tokens);
                List<Stmt> statements = parser.parse();
                interpreter.interpret(statements);
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
