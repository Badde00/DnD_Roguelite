package com.palmstam.roguelite.model.room;

import com.palmstam.roguelite.model.databaseItems.Item;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ShopRoom extends Room{
    private final String shopDescription;
    List<Item> wares;
    private Item itemDeal;

    @Builder
    public ShopRoom(String encounterType, String description, int level, String shopDescription, List<Item> wares, Item itemDeal) {
        super(encounterType, description, level);
        this.shopDescription = shopDescription;
        this.wares = wares;
        this.itemDeal = itemDeal;
        this.itemDeal.setPrice(Math.round(itemDeal.getPrice() * 0.7f));
    }
}
