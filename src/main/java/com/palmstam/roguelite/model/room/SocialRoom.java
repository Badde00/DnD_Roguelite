package com.palmstam.roguelite.model.room;

import lombok.Builder;
import lombok.Getter;
/*
* Think Sisyphus from Hades. There should be 5 total, one for every 4 levels.
*/
@Getter
public class SocialRoom extends Room {
    private String roomDescription;
    private String personDescription;

    @Builder
    public SocialRoom(String encounterType, String description, int level, String roomDescription, String personDescription) {
        super(encounterType, description, level);
        this.roomDescription = roomDescription;
        this.personDescription = personDescription; // TODO: Update after the npcs are decided upon.
    }
}
