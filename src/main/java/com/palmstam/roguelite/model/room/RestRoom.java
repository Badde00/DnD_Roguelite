package com.palmstam.roguelite.model.room;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RestRoom extends Room {
    private String roomDescription;

    @Builder
    public RestRoom(String encounterType, String description, int level, String roomDescription) {
        super(encounterType, description, level);
        this.roomDescription = roomDescription;
    }
}
