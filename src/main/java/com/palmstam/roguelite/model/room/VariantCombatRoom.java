package com.palmstam.roguelite.model.room;

import com.palmstam.roguelite.model.EnemyGroup;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@SuperBuilder
public class VariantCombatRoom extends CombatRoom {
    private String variation;
    private String variationDescription;

    public VariantCombatRoom(
            String encounterType,
            String description,
            int level,
            EnemyGroup enemyGroup,
            String mapURL,
            List<String> themes,
            String reward,
            String difficulty,
            String variation,
            String variationDescription)
    {
        super(encounterType, description, level, enemyGroup, mapURL, themes, reward, difficulty);
        this.variation = variation;
        this.variationDescription = variationDescription;
    }
}
