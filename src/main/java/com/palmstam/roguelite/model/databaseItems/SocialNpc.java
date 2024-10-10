package com.palmstam.roguelite.model.databaseItems;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "socialNpcs")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SocialNpc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String roomDescription;
    private String personDescription;
    private String gift;
}
