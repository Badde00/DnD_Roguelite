package com.palmstam.roguelite.model.room;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PuzzleRoom extends Room {
    private String roomDescription;
    private String puzzleExplanation;
    private String answer;
    private int reward;

    @Builder
    public PuzzleRoom(String encounterType, String description, int level, String roomDescription, String puzzleExplanation, String answer, int reward) {
        super(encounterType, description, level);
        this.roomDescription = roomDescription;
        this.puzzleExplanation = puzzleExplanation;
        this.answer = answer;
        this.reward = reward;
    }
}
