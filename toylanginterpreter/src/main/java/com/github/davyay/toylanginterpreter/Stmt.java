package com.github.davyay.toylanginterpreter;

// Interface for statements
interface Stmt {}

// Expression statement
class Expression implements Stmt {
    final Expr expression;

    public Expression(Expr expression) {
        this.expression = expression;
    }
}

// Assignment statement
class Assignment implements Stmt {
    final String name;
    final Expr value;

    public Assignment(String name, Expr value) {
        this.name = name;
        this.value = value;
    }
}
