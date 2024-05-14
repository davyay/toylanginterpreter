package com.github.davyay.toylanginterpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private static final String TOKEN_PATTERNS = "\\s*(?:(\\d+)|([a-zA-Z_][a-zA-Z_0-9]*)|([-+*/=;()]))\\s*";

    private final List<Token> tokens = new ArrayList<>();

    public Tokenizer(String input) {
        tokenize(input);
    }

    private void tokenize(String input) {
        Matcher matcher = Pattern.compile(TOKEN_PATTERNS).matcher(input);
        int lastEnd = 0;
        while (matcher.find()) {
            if (matcher.start() != lastEnd) {
                throw new IllegalArgumentException("Unexpected character at position " + lastEnd);
            }
            if (matcher.group(1) != null) {
                String number = matcher.group(1);
                if (number.startsWith("0") && number.length() > 1) {
                    throw new IllegalArgumentException("Invalid number format with leading zeros at position " + lastEnd);
                }
                tokens.add(new Token(TokenType.NUMBER, number));
            } else if (matcher.group(2) != null) {
                tokens.add(new Token(TokenType.IDENTIFIER, matcher.group(2)));
            } else if (matcher.group(3) != null) {
                tokens.add(new Token(TokenType.OPERATOR, matcher.group(3)));
            }
            lastEnd = matcher.end();
        }
        tokens.add(new Token(TokenType.EOF, ""));
    }

    public List<Token> getTokens() {
        return tokens;
    }
}

enum TokenType {
    NUMBER, IDENTIFIER, OPERATOR, EOF
}

class Token {
    private final TokenType type;
    private final String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", type, value);
    }
}
