package com.palmstam.roguelite.model.databaseItems;

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
}
