package com.github.abrhoda;

import com.github.abrhoda.dice.Dice;

public class Main {
    public static void main(String[] args){
        Dice dice = new Dice();
        int total = dice.roll("   3d6 + 6 - 1d4 \t");
        System.out.printf("Total for the roll is %d%n", total);
    }
}