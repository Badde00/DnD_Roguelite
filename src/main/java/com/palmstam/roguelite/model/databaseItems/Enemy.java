package com.palmstam.roguelite.model.databaseItems;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "enemies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Enemy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    private String source;
    private int page;
    private String size;
    private Type type;
    private String alignment;
    private Ac ac;
    private Hp hp;
    private Speed speed;
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;
    private String savingThrows;
    private String skills;
    private String damageVulnerabilities;
    private String damageResistances;
    private String damageImmunities;
    private String conditionImmunities;
    private String senses;
    private String languages;
    private String cr;
    private String traits;
    private String actions;
    private String bonusActions;
    private String reactions;
    private String legendaryActions;
    private String mythicActions;
    private String lairActions;
    private String regionalEffects;
    private String environment;


    @Getter
    @AllArgsConstructor
    @Embeddable
    public class Type {
        // Input will be things like "Humanoid (Dwarf)", "Fiend (Demon)" or "Beast"
        private String primaryType; // Something like "Humanoid" or "Fiend"
        private String subType; // Something like "Dwarf" or "Demon"

        public Type(String primaryType) {
            this.primaryType = primaryType;
            this.subType = "";
        }

        public String toString() {
            if (subType.isBlank()) {
                return primaryType;
            } else {
                return String.format("%s (%s)", primaryType, subType);
            }
        }
    }


    @Getter
    @AllArgsConstructor
    @Embeddable
    public class Ac {
        private int acNum;

        @Column(name = "ac_source")
        private String source;

        public Ac(int acNum) {
            this.acNum = acNum;
            this.source = "";
        }

        public String toString() {
            if (source.isBlank()) {
                return String.valueOf(acNum);
            } else {
                return String.format("%d (%s)", acNum, source);
            }
        }
    }

    @Getter
    @AllArgsConstructor
    @Embeddable
    public class Hp {
        private int hpNum;
        private String calculation;

        public Hp(int hpNum) {
            this.hpNum = hpNum;
            this.calculation = "";
        }

        public String toString() {
            return String.format("%d (%s)", hpNum, calculation);
        }
    }

    @Getter
    @ToString
    @Builder
    @Embeddable
    public static class Speed {
        private Integer walk;
        private Integer burrow;
        private Integer climb;
        private Integer fly;
        private Integer hover;
        private Integer swim;
    }
}
