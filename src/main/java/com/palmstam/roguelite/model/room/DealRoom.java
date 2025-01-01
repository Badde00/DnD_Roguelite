package com.palmstam.roguelite.model.room;

import lombok.Builder;
import lombok.Getter;

@Getter
public class DealRoom extends Room {
    private String roomDescription;
    private String deal;

    @Builder
    public DealRoom(String encounterType, String description, int level, String roomDescription, String deal) {
        super(encounterType, description, level);
        this.roomDescription = roomDescription;
        this.deal = deal;
    }
}
