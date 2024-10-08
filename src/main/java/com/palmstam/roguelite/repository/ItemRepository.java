package com.palmstam.roguelite.repository;

import com.palmstam.roguelite.model.databaseItems.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    List<Item> findByRarityIn(List<String> rarities);
    Optional<Item> findByName(String name);
}
