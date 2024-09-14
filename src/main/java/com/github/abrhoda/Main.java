package com.github.abrhoda;

import com.github.abrhoda.dice.Dice;

public class Main {
    public static void main(String[] args){
        Dice dice = new Dice();
        dice.roll("   3d6 + 6 - 1d4 \t");
    }
}