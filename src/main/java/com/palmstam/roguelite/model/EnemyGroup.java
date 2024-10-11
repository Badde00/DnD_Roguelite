package com.palmstam.roguelite.model;

import com.palmstam.roguelite.model.databaseItems.Enemy;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class EnemyGroup {
    private List<Enemy> enemies;
    private List<String> enemyTypes;
    private float groupModifier;
    private int xpAmount;



    public EnemyGroup(List<Enemy> enemies) {
        this.enemies = enemies;
        this.enemyTypes = extractEnemyTypes(enemies);
        this.groupModifier = findGroupModifier(enemies.size());
        this.xpAmount = calculateCrToXp(enemies);
    }

    public static List<String> extractEnemyTypes(List<Enemy> enemies) {
//        return enemies.stream()
//                .map(enemy -> enemy.getType().getPrimaryType())
//                .distinct().collect(Collectors.toList());
        //TODO: Fix after csv parser works
        return null;
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

    public static int calculateCrToXp(List<Enemy> enemies) {
        return enemies.stream()
                .mapToInt(enemy -> xpByCR.getOrDefault(enemy.getCr(), 0))
                .sum();
    }

    private static final Map<String, Integer> xpByCR = new HashMap<>() {{
        put("0", 10);
        put("1/8", 25);
        put("1/4", 50);
        put("1/2", 100);
        put("1", 200);
        put("2", 450);
        put("3", 700);
        put("4", 1100);
        put("5", 1800);
        put("6", 2300);
        put("7", 2900);
        put("8", 3900);
        put("9", 5000);
        put("10", 5900);
        put("11", 7200);
        put("12", 8400);
        put("13", 10000);
        put("14", 11500);
        put("15", 13000);
        put("16", 15000);
        put("17", 18000);
        put("18", 20000);
        put("19", 22000);
        put("20", 25000);
        put("21", 33000);
        put("22", 41000);
        put("23", 50000);
        put("24", 62000);
        put("25", 75000);
        put("26", 90000);
        put("27", 105000);
        put("28", 120000);
        put("29", 135000);
        put("30", 155000);
    }};
}
