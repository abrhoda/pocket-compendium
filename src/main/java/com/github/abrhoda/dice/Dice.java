package com.github.abrhoda.dice;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class Dice {
    private static final Parser parser = new Parser();

    public static void roll(String input) {
        // TODO actually use optional and not just get or throw
        List<Token> tokens = parser.tokenize(input).orElseThrow();
        for (Token t : tokens) {
            System.out.printf("Token value is %s and type is %s%n", t.value(), t.tokenType());
        }

        List<int[]> rolls = tokens.stream().filter(token -> token.tokenType() == TokenType.DICE_EXPRESSION).map(diceExpressionToken -> parser.evaluateDiceExpression(diceExpressionToken.value())).toList();

    }
}
