package com.github.davyay.toylanginterpreter;

// Interface for expressions
interface Expr {}

// Binary expression with two operands and an operator
class Binary implements Expr {
    final Expr left;
    final Token operator;
    final Expr right;

    public Binary(Expr left, Token operator, Expr right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
}

// Unary expression with a single operand and an operator
class Unary implements Expr {
    final Token operator;
    final Expr right;

    public Unary(Token operator, Expr right) {
        this.operator = operator;
        this.right = right;
    }
}

// Represents literal values
class Literal implements Expr {
    final int value;

    public Literal(int value) {
        this.value = value;
    }
}

// Represents variables using identifiers
class Variable implements Expr {
    final String name;

    public Variable(String name) {
        this.name = name;
    }
}

// Represents expressions within parentheses
class Grouping implements Expr {
    final Expr expression;

    public Grouping(Expr expression) {
        this.expression = expression;
    }
}
