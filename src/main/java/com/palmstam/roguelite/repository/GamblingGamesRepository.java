package com.palmstam.roguelite.repository;

import com.palmstam.roguelite.model.databaseItems.GamblingGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GamblingGamesRepository extends JpaRepository<GamblingGame, Integer> {
}
