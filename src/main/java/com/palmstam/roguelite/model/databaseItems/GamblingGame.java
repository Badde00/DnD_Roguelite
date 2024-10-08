package com.palmstam.roguelite.model.databaseItems;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "gamblingGames")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GamblingGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roomDescription;
    private String gameType;
    private String rules;
    private int minimumBet;
}
