package com.palmstam.roguelite.model.databaseItems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palmstam.roguelite.model.RollDice;
import com.palmstam.roguelite.model.data.ItemDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "items")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, length = 1000)
    private String name;

    @Column(length = 8000)
    private String description;

    private int price;
    private String rarity;
    private boolean isMundane;
    private String source;
    private int page;
    private List<String> types;


    @JsonIgnore
    public static int getPrice(Item item) {
        RollDice dice = new RollDice();
        if (item.getRarity() == null) return -3;
        int price = switch (item.getRarity().toLowerCase()) {
            case "common" -> (dice.rollDiceSum(6) + 1) * 10;
            case "uncommon" -> dice.rollDiceSum(6) * 100;
            case "rare" -> dice.rollDiceSum(2, 10) * 1000;
            case "very rare" -> (dice.rollDiceSum(4) + 1) * 10_000;
            case "legendary" -> dice.rollDiceSum(2, 6) * 25_000;
            case "artifact" -> dice.rollDiceSum(10) * 50_000;
            default -> -2;
        };
        if (item.types.contains("potion") || item.types.contains("scroll")) {
            price /= 2;
        }
        return price;
    }

    @JsonIgnore
    public static String cleanType(String type) {
        if (type.contains("(<a")) {
            return type.split(" \\(<a")[0];
        }
        return type;
    }

    public Item (ItemDTO itemDTO) {
        this.name = itemDTO.getName();
        this.description = itemDTO.getEntries() != null ? (String) itemDTO.getEntries().getFirst() : "";
        this.rarity = itemDTO.getRarity();
        this.types = itemDTO.getTypes();
        this.isMundane = itemDTO.isMundane();
        this.source = itemDTO.getSource();
        this.page = itemDTO.getPage();

        if (itemDTO.getPrice() != 0) {
            this.price = itemDTO.getPrice() / 100;
        } else if (itemDTO.isMundane()) {
            this.setPrice(-1);
        } else {
            this.price = getPrice(this);
        }
    }
}
