package com.palmstam.roguelite.model.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenerateRoomDTO {
    private String encounterType;
    private Integer level;
    private Integer numberOfPlayers;
}
