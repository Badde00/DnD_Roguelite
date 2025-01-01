package com.palmstam.roguelite.model.room;

import lombok.Builder;
import lombok.Getter;

@Getter
public class GamblingRoom extends Room {
    private String roomDescription;
    private String gameType;
    private String rules;
    private int minimumBet;

    @Builder
    public GamblingRoom(String encounterType, String description, int level, String roomDescription, String gameType, String rules, int minimumBet) {
        super(encounterType, description, level);
        this.roomDescription = roomDescription;
        this.gameType = gameType;
        this.rules = rules;
        this.minimumBet = minimumBet;
    }
}
