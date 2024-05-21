package com.github.davyay.toylanginterpreter;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private final Map<String, Integer> symbols = new HashMap<>();

    public boolean isDefined(String name) {
        return symbols.containsKey(name);
    }

    public void define(String name, int value) {
        symbols.put(name, value);  
    }

    public int get(String name) {
        if (!symbols.containsKey(name)) {
            throw new RuntimeException("Variable '" + name + "' not initialized");
        }
        return symbols.get(name);
    }

    public void print() {
        for (Map.Entry<String, Integer> entry : symbols.entrySet()) {
            if (entry.getValue() == null) {
                System.out.println(entry.getKey() + " is declared but not initialized");
            } else {
                System.out.println(entry.getKey() + " = " + entry.getValue());
            }
        }
    }

    public void clear() {
        symbols.clear();
    }
}