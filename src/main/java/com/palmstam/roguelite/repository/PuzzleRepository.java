package com.palmstam.roguelite.repository;

import com.palmstam.roguelite.model.databaseItems.Puzzle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PuzzleRepository extends JpaRepository<Puzzle, Integer> {
}
