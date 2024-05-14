package com.github.davyay.toylanginterpreter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public List<Stmt> parse() {
        List<Stmt> statements = new ArrayList<>();
        while (!isAtEnd()) {
            statements.add(parseAssignment());
        }
        return statements;
    }

    private Stmt parseAssignment() {
        Token identifier = consume(TokenType.IDENTIFIER, "Expect identifier.");
        consume(TokenType.OPERATOR, "Expect '='.", "=");
        Expr expr = parseExpression();
        consume(TokenType.OPERATOR, "Expect ';'.", ";");
        return new Assignment(identifier.getValue(), expr);
    }

    private Expr parseExpression() {
        return parseAddition();
    }

    private Expr parseAddition() {
        Expr expr = parseTerm();
        while (match(TokenType.OPERATOR, "+", "-")) {
            Token operator = previous();
            Expr right = parseTerm();
            expr = new Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr parseTerm() {
        Expr expr = parseFactor();
        while (match(TokenType.OPERATOR, "*", "/")) {
            Token operator = previous();
            Expr right = parseFactor();
            expr = new Binary(expr, operator, right);
        }
        return expr;
    }

    private Expr parseFactor() {
        if (match(TokenType.NUMBER)) {
            return new Literal(Integer.parseInt(previous().getValue()));
        } else if (match(TokenType.IDENTIFIER)) {
            return new Variable(previous().getValue());
        } else if (match(TokenType.OPERATOR, "(")) {
            Expr expr = parseExpression();
            consume(TokenType.OPERATOR, "Expect ')'.", ")");
            return new Grouping(expr);
        } else if (match(TokenType.OPERATOR, "+", "-")) {
            Token operator = previous();
            Expr right = parseFactor();
            return new Unary(operator, right);
        }
        throw new ParseException("Unexpected token.");
    }

    private boolean match(TokenType type, String... values) {
        if (check(type) && (values.length == 0 || Arrays.asList(values).contains(peek().getValue()))) {
            advance();
            return true;
        }
        return false;
    }

    private Token consume(TokenType type, String message, String... values) {
        if (check(type) && (values.length == 0 || Arrays.asList(values).contains(peek().getValue()))) {
            return advance();
        }
        throw new ParseException(message + " Found: " + peek().getValue());
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().getType() == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}

class ParseException extends RuntimeException {
    public ParseException(String message) {
        super(message);
    }
}
