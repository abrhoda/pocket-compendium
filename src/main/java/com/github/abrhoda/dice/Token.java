package com.github.abrhoda.dice;

record Token(String value, TokenType tokenType) {
    public boolean isValid() {
        // being a good engineer and null checking even though this should be impossible.
        if (this.value == null || this.value.isBlank()) {
            return false;
        }
        switch (this.tokenType) {
            case CONSTANT: {
                return this.value.chars().allMatch(Character::isDigit);
            }
            case DICE_EXPRESSION: {
                // check that we start with digits
                // check there is exactly 1 'd'/'D'
                // check that only digits happen after that
            }
            case OPERATOR: {
                return (this.value.equalsIgnoreCase("+") || this.value.equalsIgnoreCase("-"));
            }
        }
        return false;
    }
}
