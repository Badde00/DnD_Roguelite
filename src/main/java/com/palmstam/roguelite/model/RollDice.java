package com.palmstam.roguelite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class RollDice {
    public int[] rollDice(int amount, int sides) {
        ArrayList<Integer> results = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            results.add(random.nextInt(sides) + 1);
        }
        return results.stream().mapToInt(i -> i).toArray();
    }

    public int[] rollDice(int sides) {
        return rollDice(1, sides);
    }

    public int rollDiceSum(int amount, int sides) {
        return Arrays.stream(rollDice(amount, sides)).sum();
    }

    public int rollDiceSum(int sides) {
        return rollDiceSum(1, sides);
    }
}
