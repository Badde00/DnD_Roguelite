package com.palmstam.roguelite.model;

import jakarta.persistence.Tuple;
import lombok.Getter;

import java.util.*;

public class Tables {
    private final Random random;
    private final Map<String, Integer[]> difficultyMapping = Map.of(
            "Currency", new Integer[]{1, 2, 3},
            "Upgrade Armor", new Integer[]{1, 2, 3},
            "Upgrade Weapon/Health", new Integer[]{1, 2},
            "Upgrade Class", new Integer[]{2, 3},
            "Patronage", new Integer[]{1, 2},
            "Feat", new Integer[]{0, 1, 2, 3}
    );
    private final String[] difficulties = new String[]{
            "Easy", "Medium", "Hard", "Deadly"
    };
    private final String[] lowTheme = new String[]{
            "Beast", "Construct", "Humanoid", "Monstrosity", "Undead"
    };
    private final String[] midTheme = new String[]{
            "Aberration", "Beast", "Construct", "Dragon", "Elemental",
            "Fey", "Fiend", "Giant", "Humanoid", "Monstrosity", "Undead"
    };
    private final String[] highTheme = new String[]{
            "Aberration", "Celestial", "Construct", "Dragon", "Elemental",
            "Fiend", "Giant", "Humanoid", "Monstrosity", "Undead"
    };
    private final String[] endTheme = new String[]{
            "Aberration", "Construct", "Dragon", "Fiend", "Giant",
            "Monstrosity", "Undead"
    };
    public static final Map<String, Integer> xpByCR = Map.ofEntries(
            Map.entry("0", 10), Map.entry("1/8", 25), Map.entry("1/4", 50),
            Map.entry("1/2", 100), Map.entry("1", 200), Map.entry("2", 450),
            Map.entry("3", 700), Map.entry("4", 1100), Map.entry("5", 1800),
            Map.entry("6", 2300), Map.entry("7", 2900), Map.entry("8", 3900),
            Map.entry("9", 5000), Map.entry("10", 5900), Map.entry("11", 7200),
            Map.entry("12", 8400), Map.entry("13", 10000), Map.entry("14", 11500),
            Map.entry("15", 13000), Map.entry("16", 15000), Map.entry("17", 18000),
            Map.entry("18", 20000), Map.entry("19", 22000), Map.entry("20", 25000),
            Map.entry("21", 33000), Map.entry("22", 41000), Map.entry("23", 50000),
            Map.entry("24", 62000), Map.entry("25", 75000), Map.entry("26", 90000),
            Map.entry("27", 105000), Map.entry("28", 120000), Map.entry("29", 135000),
            Map.entry("30", 155000)
    );
    public static final int[][] xpThresholdsByLevelPerPerson = {
            {25, 50, 75, 100},
            {50, 100, 150, 200},
            {75, 150, 225, 400},
            {125, 250, 375, 500},
            {250, 500, 750, 1100},
            {300, 600, 900, 1400},
            {350, 750, 1100, 1700},
            {450, 900, 1400, 2100},
            {550, 1100, 1600, 2400},
            {600, 1200, 1900, 2800},
            {800, 1600, 2400, 3600},
            {1000, 2000, 3000, 4500},
            {1100, 2200, 3400, 5100},
            {1250, 2500, 3800, 5700},
            {1400, 2800, 4300, 6400},
            {1600, 3200, 4800, 7200},
            {2000, 3900, 5900, 8800},
            {2100, 4200, 6300, 9500},
            {2400, 4900, 7300, 10900},
            {2800, 5700, 8500, 12700}
    };


    public Tables() {
        random = new Random();
    }

    @Getter
    private static class Table<T> {
        private final List<T> items;
        private final List<Integer> probabilities;

        public Table(List<T> items, List<Integer> probabilities) {
            this.items = items;
            this.probabilities = probabilities;
        }

    }

    private Table<String> currencies = new Table<>(
            List.of("Gold", "Electrum", "Gemstones", "Faith", "Artwork"),
            List.of(40, 15, 15, 15, 15)
    );
    private Table<String> combatReward = new Table<>(
            List.of("Currency", "Upgrade Weapon/Health", "Upgrade Armor", "Upgrade Class", "Patronage", "Feat"),
            List.of(50, 10, 10, 10, 10, 10)
    );

    private <T> String rollOnTable(Table<T> table) {
        RollDice dice = new RollDice();
        try {
            return dice.rollD100Table(table.getItems(), table.getProbabilities()).toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Something went wrong in rollOnTable");
            return "";
        }
    }

    public String rollCurrencies() {
        return rollOnTable(currencies);
    }

    public String rollCombatReward() {
        return rollOnTable(combatReward);
    }

    public int rollCurrencyRewardPerPerson(int level, boolean isCurrencyRoom, boolean isGoldRoom) {
        int rewardPerPerson = 0;
        RollDice dice = new RollDice();

        if (level >= 1 && level <= 5) {
            rewardPerPerson = dice.rollDiceSum(10) * 5 * (int) Math.round(Math.pow(2, level - 1));
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

    public <T> T chooseRandom(List<T> itemList) {
        return itemList.get(this.random.nextInt(itemList.size()));
    }

    public <T> T chooseRandom(T[] itemArr) {
        return itemArr[this.random.nextInt(itemArr.length)];
    }

    public String rollDifficulty(String reward) {
        return difficulties[chooseRandom(difficultyMapping.get(reward))];
    }

    public String rollTheme(int level) {
        if (level <= 4) {
            return chooseRandom(lowTheme);
        } else if (level <= 10) {
            return chooseRandom(midTheme);
        } else if (level <= 15) {
            return chooseRandom(highTheme);
        } else {
            return chooseRandom(endTheme);
        }
    }

    public static float findGroupModifier(int groupSize) {
        if (groupSize <= 0) return 0;
        return switch (groupSize) {
            case 1 -> 1.0f;
            case 2 -> 1.5f;
            case 3, 4, 5, 6 -> 2.0f;
            case 7, 8, 9, 10 -> 2.5f;
            case 11, 12, 13, 14 -> 3.0f;
            default -> 4.0f;
        };
    }

    public int getDifficultyIndex(String difficulty) {
        for (int i = 0; i < difficulties.length; i++) {
            if (difficulties[i].equalsIgnoreCase(difficulty)) {
                return i;
            }
        }
        return -1; // Return -1 if the difficulty is not found
    }
}


/*
* If you have the data
```
private int[][] xpThresholdsByLevelPerPerson = {
    {25, 50, 75, 100},
    {50, 100, 150, 200},
//Keeps going. It's of size [20][4]
}

private Map<String, Integer> xpByCR = Map.ofEntries(
            Map.entry("0", 10), Map.entry("1/8", 25), Map.entry("1/4", 50),
            Map.entry("1/2", 100), Map.entry("1", 200), Map.entry("2", 450),
            Map.entry("3", 700), Map.entry("4", 1100), Map.entry("5", 1800),
// Keeps going, it's got keys for integers up to 30, within strings
);

public static float findGroupModifier(int groupSize) {
        if (groupSize <= 0) return 0;
        return switch (groupSize) {
            case 1 -> 1.0f;
            case 2 -> 1.5f;
            case 3, 4, 5, 6 -> 2.0f;
            case 7, 8, 9, 10 -> 2.5f;
            case 11, 12, 13, 14 -> 3.0f;
            default -> 4.0f;
        };
    }
```
Can you make a function called generateCrs(int level, int numberOfPlayers) that returns a List<String> of cr's that doesn't exceed the xp threshold of
*/
