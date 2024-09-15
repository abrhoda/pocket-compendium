package com.github.abrhoda.dice;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DiceTest {

    private final Dice dice = new Dice();

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\t", "+ "})
    public void roll_ShouldReturn0ForWhitespace(String input) {
        assertEquals(0, dice.roll(input));
    }

    private static Stream<Arguments> provideConstantsForConstantsOnly() {
        return Stream.of(
                Arguments.of("1", 1),
                Arguments.of("1+1", 2),
                Arguments.of("2-1", 1)
        );
    }

    @ParameterizedTest
    @MethodSource("provideConstantsForConstantsOnly")
    public void roll_ShouldReturnIntForConstantOnly(String input, int expected) {
        assertEquals(expected, dice.roll(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"2d", "1x2"})
    public void roll_ShouldReturn0WhenDiceExpressionHasInvalidSyntax(String input) {
        assertEquals(0, dice.roll(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"0d4", "1d0"})
    public void roll_ShouldReturn0WhenDiceExpressionHas0CountOr0Sides(String input) {
        assertEquals(0, dice.roll(input));
    }

    private static Stream<Arguments> provideDiceExpressionsOnly() {
        return Stream.of(
                Arguments.of("d4", 1, 4),
                Arguments.of("2d8", 2, 16)
        );
    }

    @ParameterizedTest
    @MethodSource("provideDiceExpressionsOnly")
    public void roll_ShouldReturnSumOfDiceExpressionsBetweenBounds(String input, int min, int max) {
        int roll = dice.roll(input);
        assertTrue((min <= roll && roll < max));
    }

    private static Stream<Arguments> provideComplexExpressions() {
        return Stream.of(
                Arguments.of("d4 +8", 9, 12),
                Arguments.of("2d8 - 2 + 1d4", 1, 18)
        );
    }
    @ParameterizedTest
    @MethodSource("provideComplexExpressions")
    public void roll_ShouldReturnSumOfComplexExpressionBetweenBounds(String input, int min, int max) {
        int roll = dice.roll(input);
        System.out.println(roll); // keeping this here for later in case.
        assertTrue((min <= roll && roll < max));
    }
}
