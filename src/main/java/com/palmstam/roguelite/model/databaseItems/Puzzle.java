package com.palmstam.roguelite.model.databaseItems;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "puzzles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Puzzle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roomDescription;
    private String puzzleExplanation;
    private String answer;
}
