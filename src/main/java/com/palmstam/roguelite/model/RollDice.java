package com.palmstam.roguelite.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public <T> T rollD100Table(List<T> items, List<Integer> probabilities) throws Exception {
        if (items.size() != probabilities.size()) {
            throw new Exception("Invalid list sizes. items and probabilities must have an equal size.");
        }
        if (probabilities.stream().mapToInt(Integer::intValue).sum() != 100) {
            throw new Exception("Invalid probability list. Internal values must add up to 100.");
        }
        Random random = new Random();
        int roll = random.nextInt(100);
        for (int i = 0; i < probabilities.size(); i++) {
            if (roll > probabilities.get(i)) {
                roll -= probabilities.get(i);
            } else {
                return items.get(i);
            }
        }
        throw new Exception("An unknown error occurred in rollD100Table");
    }
}
