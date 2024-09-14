package com.github.abrhoda;

import com.github.abrhoda.dice.Parser;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.tokenize("   3d4 + 3 + 12d6 \t");
    }
}