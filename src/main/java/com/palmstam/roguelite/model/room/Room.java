package com.palmstam.roguelite.model.room;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public abstract class Room {
    private String encounterType;
    private String description;
    private int level;
}
