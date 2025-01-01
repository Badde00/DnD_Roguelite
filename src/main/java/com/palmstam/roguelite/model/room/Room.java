package com.palmstam.roguelite.model.room;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public abstract class Room {
    private String encounterType;
    private String description;
    private int level;
    @Setter
    @Builder.Default
    private boolean hidden = false;

    public Room(String encounterType, String description, int level) {
        this.encounterType = encounterType;
        this.description = description;
        this.level = level;
    }
}
