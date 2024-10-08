package com.palmstam.roguelite.model.room;

import com.palmstam.roguelite.model.EnemyGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class CombatRoom extends Room {
    private EnemyGroup enemyGroup;
    private String mapURL;
    private List<String> themes;
    private String reward;
    private String difficulty;

    public CombatRoom(String encounterType, String description, int level, EnemyGroup enemyGroup, String mapURL, List<String> themes, String reward, String difficulty) {
        super(encounterType, description, level);
        this.enemyGroup = enemyGroup;
        this.mapURL = mapURL;
        this.themes = new ArrayList<>(themes);
        this.reward = reward;
        this.difficulty = difficulty;
    }
}
