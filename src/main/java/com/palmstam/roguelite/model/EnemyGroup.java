package com.palmstam.roguelite.model;

import com.palmstam.roguelite.model.databaseItems.Enemy;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.palmstam.roguelite.model.Tables.xpByCR;

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
        this.groupModifier = Tables.findGroupModifier(enemies.size());
        this.xpAmount = calculateCrToXp(enemies);
    }

    public static List<String> extractEnemyTypes(List<Enemy> enemies) {
        return enemies.stream()
                .map(enemy -> enemy.getType().getPrimaryType())
                .distinct().collect(Collectors.toList());
    }



    public static int calculateCrToXp(List<Enemy> enemies) {
        return enemies.stream()
                .mapToInt(enemy -> xpByCR.getOrDefault(enemy.getCr(), 0))
                .sum();
    }
}
