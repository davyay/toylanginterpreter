package com.github.davyay.toylanginterpreter;

import java.util.List;

public class Interpreter {
    private final SymbolTable symbolTable = new SymbolTable();

    public void interpret(List<Stmt> statements) {
        symbolTable.clear();
        try {
            for (Stmt statement : statements) {
                execute(statement);
            }
        } catch (RuntimeException error) {
            System.err.println("Error: " + error.getMessage());
            return;
        }
        symbolTable.print();
    }

    private void execute(Stmt stmt) {
        if (stmt instanceof Expression) {
            executeExpressionStmt((Expression) stmt);
        } else if (stmt instanceof Assignment) {
            executeAssignmentStmt((Assignment) stmt);
        }
    }

    private void executeExpressionStmt(Expression stmt) {
        evaluate(stmt.expression);
    }

    private void executeAssignmentStmt(Assignment stmt) {
        Object value = evaluate(stmt.value);
        symbolTable.define(stmt.name, (Integer) value);
    }

    private Object evaluate(Expr expr) {
        if (expr instanceof Binary) {
            return evaluateBinaryExpr((Binary) expr);
        } else if (expr instanceof Unary) {
            return evaluateUnaryExpr((Unary) expr);
        } else if (expr instanceof Literal) {
            return evaluateLiteralExpr((Literal) expr);
        } else if (expr instanceof Variable) {
            return evaluateVariableExpr((Variable) expr);
        } else if (expr instanceof Grouping) {
            return evaluateGroupingExpr((Grouping) expr);
        }
        throw new RuntimeException("Unknown expression type");
    }

    private Object evaluateBinaryExpr(Binary expr) {
        Object left = evaluate(expr.left);
        Object right = evaluate(expr.right);
        switch (expr.operator.getValue()) {
            case "+":
                return (int) left + (int) right;
            case "-":
                return (int) left - (int) right;
            case "*":
                return (int) left * (int) right;
            case "/":
                if ((int) right == 0) {
                    throw new RuntimeException("Division by zero");
                }
                return (int) left / (int) right;
            default:
                throw new RuntimeException("Unknown operator: " + expr.operator.getValue());
        }
    }

    private Object evaluateUnaryExpr(Unary expr) {
        Object right = evaluate(expr.right);
        switch (expr.operator.getValue()) {
            case "-":
                return -(int) right;
            case "+":
                return (int) right;
        }
        throw new RuntimeException("Unknown operator");
    }

    private Object evaluateLiteralExpr(Literal expr) {
        return expr.value;
    }

    private Object evaluateVariableExpr(Variable expr) {
        if (!symbolTable.isDefined(expr.name)) {
            throw new RuntimeException("Uninitialized variable '" + expr.name + "'");
        }
        return symbolTable.get(expr.name);
    }

    private Object evaluateGroupingExpr(Grouping expr) {
        return evaluate(expr.expression);
    }
}