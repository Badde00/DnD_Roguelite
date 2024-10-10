package com.palmstam.roguelite.model.room;

import com.palmstam.roguelite.model.RollCurrency;
import com.palmstam.roguelite.model.databaseItems.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TreasureRoom extends Room {
    private String[] currencies;
    private List<Item> items;

    @Builder
    public TreasureRoom(String encounterType, String description, int level, String[] currencies, List<Item> items) {
        super(encounterType, description, level);
        this.currencies = currencies;
        this.items = items;

    }
}
