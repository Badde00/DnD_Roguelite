package com.palmstam.roguelite.repository;

import com.palmstam.roguelite.model.databaseItems.Deal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DealRepository extends JpaRepository<Deal, Integer> {
}
