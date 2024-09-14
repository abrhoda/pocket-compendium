package com.github.abrhoda.dice;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    public void tokenize(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            return;
        }

        String sanitized = rawInput.replaceAll("\\s+","");

        List<Token> tokens = new ArrayList<>();
        int start = 0;
        int current = 0;
        while (current < sanitized.length()) {
            char c = sanitized.charAt(current);
            if (c == '+' || c == '-') {
                String chunk = sanitized.substring(start, current);
                TokenType tokenType = chunk.chars().allMatch(Character::isDigit) ? TokenType.CONSTANT : TokenType.DICE_EXPRESSION;
                tokens.add(new Token(chunk, tokenType));
                String op = String.valueOf(c);
                tokens.add(new Token(op, TokenType.OPERATOR));
                start = current + 1;
            }
            current++;
        }
        String chunk = sanitized.substring(start);
        TokenType tokenType = chunk.chars().allMatch(Character::isDigit) ? TokenType.CONSTANT : TokenType.DICE_EXPRESSION;
        tokens.add(new Token(chunk, tokenType));

        for (Token token : tokens) {
            System.out.printf("TokenType = %s and token value = '%s'%n", token.tokenType(), token.value());
        }
    }
}
