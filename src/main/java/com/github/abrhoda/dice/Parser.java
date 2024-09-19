package com.github.abrhoda.dice;

import java.util.*;

class Parser {
    private static final Map<Character, Integer> OPERATOR_PRECEDENCE = Map.of('+', 1, '-', 1, '/', 2, '*', 2);

    List<String> tokenize(String input) {
        // preprocess into tokens so we can convert to postfix notation easier.
        List<String> tokens = new ArrayList<>();
        int j = 0;
        for (int i=0; i<input.length(); i++){
            char c = input.charAt(i);
            if (c == '+' || c == '-' || c =='*' || c == '/' || c == '(' || c == ')') {
                if (j != i) {
                    tokens.add(input.substring(j, i));
                }
                j = i + 1;
                tokens.add(String.valueOf(c));
            }
        }
        // add the remaining substring as a token
        tokens.add(input.substring(j));

        return tokens;
    }

    List<String> infixToPostfix(List<String> tokens) {
        List<String> postfix = new ArrayList<>();
        Stack<Character> operatorStack = new Stack<>();

        return postfix;
    }

}
