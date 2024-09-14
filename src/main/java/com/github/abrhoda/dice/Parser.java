package com.github.abrhoda.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

class Parser {

    private static final Random random = new Random();
    /**
     * Splits the dice notation string into a list of tokens.
     * <p>
     * Note: list of tokens works here because there's no parens and both operators have the same precedence. If parens,
     * "*", or "/" are added, this should be an abstract syntax tree where depth first operations are done. With all as
     * the same precedence, a left to right reading straight through a list works fine.
     *
     * @param rawInput the full dice notation to be split into tokens.
     * @return list of parsed Tokens
     */
    public Optional<List<Token>> tokenize(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            return Optional.empty();
        }

        String sanitized = rawInput.replaceAll("\\s+","");

        //
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
        return Optional.of(tokens);
    }

    /**
     *
     * @param diceExpression
     * @return
     */
    public int[] evaluateDiceExpression(String diceExpression) {
        int index = diceExpression.toLowerCase().indexOf('d');

        // these have been validated for format so ignoring `NumberFormatException`
        int count = Integer.parseInt(diceExpression.substring(0, index));
        int sides = Integer.parseInt(diceExpression.substring(index+1));
        int[] rolls = new int[count];

        for (int i=0; i<count; ++i) {
            int j = (random.nextInt(sides) + 1);
            System.out.printf("Rolled %d on [1, %d] interval%n", j, sides);
            rolls[i] = j;
        }

        return rolls;
    }

}
