package com.github.abrhoda.dice;

import java.util.*;

public class Dice {
    private static final Random random = new Random();

    /**
     * Evaluate a very simple dice notation string. It must follow {count}d{sides} (called a dice expression) and any
     * number of constants. The only operations supported at +/- for now.
     *
     * @param input very dice notation string.
     * @return sum of the dice notation string or 0 if invalid.
     */
    // TODO use OptionalInt instead of int and returning a 0.
    public int roll(String input) {
        List<Token> tokens = tokenize(input);
        if (tokens == null || tokens.isEmpty()) {
            return 0;
        }

        int total = 0;
        boolean shouldAdd = true;
        for (Token token : tokens) {
            switch (token.tokenType()) {
                case CONSTANT, DICE_EXPRESSION: {
                    int sum = sum(token);
                    if (shouldAdd) {
                        total += sum;
                    } else {
                        total -= sum;
                    }
                    break;
                }
                case OPERATOR: {
                    shouldAdd = (token.value().equalsIgnoreCase("+"));
                    break;
                }
            }
        }
        return total;
    }
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
    // TODO use Optional instead of null
    private List<Token> tokenize(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            return null;
        }

        String sanitized = rawInput.replaceAll("\\s+","");
        List<Token> tokens = new ArrayList<>();
        int start = 0;
        int current = 0;
        while (current < sanitized.length()) {
            char c = sanitized.charAt(current);
            if (c == '+' || c == '-') {
                tokens.add(getTokenFromChunk(sanitized.substring(start, current)));
                tokens.add(getTokenFromChunk(String.valueOf(c)));
                start = current + 1;
            }
            current++;
        }
        tokens.add(getTokenFromChunk(sanitized.substring(start)));
        return tokens;
    }

    /**
     * Turns a piece of the rawInput dice notation into a token with an associated type.
     *
     * @param chunk a substring from the rawInput dice notation.
     * @return Token created from the substring input with an associated TokenType
     */
    private Token getTokenFromChunk(String chunk) {
        if (chunk.equalsIgnoreCase("+") || chunk.equalsIgnoreCase("-")) {
            return new Token(chunk, TokenType.OPERATOR);
        }

        if (chunk.chars().allMatch(Character::isDigit)) {
            return new Token(chunk, TokenType.CONSTANT);
        }

        // TODO This should validate the DICE_EXPRESSION type and return an Optional<Token> instead of just Token for when the chunk isn't a valid token.
        return new Token(chunk, TokenType.DICE_EXPRESSION);
    }

    /**
     * Given a token that is not of TokenType.OPERATOR, gets an int for the token's value
     *
     * @param token a Token with a TokenType that is not TokenType.OPERATOR
     * @return an int parsed from the constant or from the dice expression
     */
    private int sum(Token token) {
        if (token.tokenType() == TokenType.CONSTANT) {
            return Integer.parseInt(token.value());
        }

        return Arrays.stream(evaluateDiceExpression(token.value())).sum();
    }

    /**
     * Evaluates dice expression string (of {count}{d}{sides} syntax without the braces) into an array of rolls.
     *
     * @param diceExpression string dice notation that needs to be evaluated for a set of rolls.
     * @return an array of the individual rolls made for the dice expression
     */
     private int[] evaluateDiceExpression(String diceExpression) {
         int index = diceExpression.toLowerCase().indexOf('d');

         // these have been validated for format so ignoring `NumberFormatException`
         int count = Integer.parseInt(diceExpression.substring(0, index));
         int sides = Integer.parseInt(diceExpression.substring(index+1));

         int[] rolls = new int[count];
         for (int i=0; i<count; ++i) {
             int j = (random.nextInt(sides) + 1);
             rolls[i] = j;
         }
         return rolls;
    }
}
