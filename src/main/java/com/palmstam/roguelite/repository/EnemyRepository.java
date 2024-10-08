package com.palmstam.roguelite.repository;

import com.palmstam.roguelite.model.databaseItems.Enemy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnemyRepository extends JpaRepository<Enemy, Integer> {
}
