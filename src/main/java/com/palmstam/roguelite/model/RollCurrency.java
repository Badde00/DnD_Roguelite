package com.palmstam.roguelite.model;

public class RollCurrency {
    public static int rollCurrencyRewardPerPerson(int level, boolean isCurrencyRoom, boolean isGoldRoom) {
        int rewardPerPerson = 0;
        RollDice dice = new RollDice();

        if (level >= 1 && level <= 5) {
            rewardPerPerson = dice.rollDiceSum(10) * 5 * (int)Math.round(Math.pow(2, level - 1));
        } else if (level >= 6 && level <= 10) {
            int tempLevel = level % 2 == 1 ? level - 1 : level;
            rewardPerPerson = 50 * tempLevel + 100 * dice.rollDiceSum(tempLevel);
        } else if (level >= 11 && level <= 15) {
            rewardPerPerson = 100 * level + 50 * dice.rollDiceSum(4, 10);
        } else {
            rewardPerPerson = 500 * level + 1000 * dice.rollDiceSum(20);
        }

        if (isCurrencyRoom) {
            rewardPerPerson *= 2;
        }
        if (isGoldRoom) {
            rewardPerPerson *= 2;
        }

        return rewardPerPerson;
    }


    public static int rollCurrencyRewardPerPerson(int level) {
        return rollCurrencyRewardPerPerson(level, false, false);
    }
}
