package com.palmstam.roguelite.model.databaseItems;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.palmstam.roguelite.model.RollDice;
import jakarta.persistence.*;
import lombok.*;

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

    @Column(length = 2000)
    private String description;

    private int price;
    private String rarity;
    private boolean isMundane;
    private String source;
    private int page;
    private String type;


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
        if (item.type.equals("potion") || item.type.equals("scroll")) {
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
}
