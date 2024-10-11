package com.palmstam.roguelite.model.databaseItems;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.palmstam.roguelite.model.data.EnemyCsvParserService;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @CsvBindByName(column = "Name")
    private String name;

    @CsvBindByName(column = "Source")
    private String source;

    @CsvBindByName(column = "Page")
    private int page;

    @CsvBindByName(column = "Size")
    private String size;

    @CsvBindByName(column = "Type")
    private String type;
//    @Embedded
//    private Type type;

    @CsvBindByName(column = "Alignment")
    private String alignment;

    @CsvBindByName(column = "AC")
    private String ac;
//    @Embedded
//    private Ac ac;

    @CsvBindByName(column = "HP")
    private String hp;
//    @Embedded
//    private Hp hp;

    @CsvBindByName(column = "Speed")
    private String speed;
//    @Embedded
//    private Speed speed;

    @CsvBindByName(column = "Strength")
    private int strength;

    @CsvBindByName(column = "Dexterity")
    private int dexterity;

    @CsvBindByName(column = "Constitution")
    private int constitution;

    @CsvBindByName(column = "Intelligence")
    private int intelligence;

    @CsvBindByName(column = "Wisdom")
    private int wisdom;

    @CsvBindByName(column = "Charisma")
    private int charisma;

    @CsvBindByName(column = "Saving Throws")
    private String savingThrows;

    @CsvBindByName(column = "Skills")
    private String skills;

    @CsvBindByName(column = "Damage Vulnerabilities")
    private String damageVulnerabilities;

    @CsvBindByName(column = "Damage Resistances")
    private String damageResistances;

    @CsvBindByName(column = "Damage Immunities")
    private String damageImmunities;

    @CsvBindByName(column = "Condition Immunities")
    private String conditionImmunities;

    @CsvBindByName(column = "Senses")
    private String senses;

    @CsvBindByName(column = "Languages")
    private String languages;

    @CsvBindByName(column = "CR")
    private String cr;

    @CsvBindByName(column = "Traits")
    private String traits;

    @CsvBindByName(column = "Actions")
    private String actions;

    @CsvBindByName(column = "Bonus Actions")
    private String bonusActions;

    @CsvBindByName(column = "Reactions")
    private String reactions;

    @CsvBindByName(column = "Legendary Actions")
    private String legendaryActions;

    @CsvBindByName(column = "Mythic Actions")
    private String mythicActions;

    @CsvBindByName(column = "Lair Actions")
    private String lairActions;

    @CsvBindByName(column = "Regional Effects")
    private String regionalEffects;

    @CsvBindByName(column = "Environment")
    private String environment;


//    @Getter
//    @Embeddable
//    @NoArgsConstructor
//    public class Type {
//        // Input will be things like "Humanoid (Dwarf)", "Fiend (Demon)" or "Beast"
//        private String primaryType; // Something like "Humanoid" or "Fiend"
//        private String subType; // Something like "Dwarf" or "Demon"
//
//        public Type(String typeString) {
//            setType(typeString);
//        }
//
//        public String toString() {
//            if (subType.isBlank()) {
//                return primaryType;
//            } else {
//                return String.format("%s (%s)", primaryType, subType);
//            }
//        }
//
//        public void setType(String typeString) {
//            if (typeString.contains("(")) {
//                String[] parts = typeString.split("\\(");
//                this.primaryType = parts[0].trim();
//                this.subType = parts[1].replace(")", "");
//            } else {
//                this.primaryType = typeString.trim();
//                this.subType = "";
//            }
//        }
//    }
//
//
//    @Getter
//    @AllArgsConstructor
//    @Embeddable
//    public class Ac {
//        private int acNum;
//
//        @Column(name = "ac_source")
//        private String source;
//
//        public Ac(int acNum) {
//            this.acNum = acNum;
//            this.source = "";
//        }
//
//        public String toString() {
//            if (source.isBlank()) {
//                return String.valueOf(acNum);
//            } else {
//                return String.format("%d (%s)", acNum, source);
//            }
//        }
//    }
//
//    @Getter
//    @AllArgsConstructor
//    @Embeddable
//    public class Hp {
//        private int hpNum;
//        private String calculation;
//
//        public Hp(int hpNum) {
//            this.hpNum = hpNum;
//            this.calculation = "";
//        }
//
//        public String toString() {
//            return String.format("%d (%s)", hpNum, calculation);
//        }
//    }
//
//    @Getter
//    @ToString
//    @Builder
//    @Embeddable
//    public static class Speed {
//        private Integer walk;
//        private Integer burrow;
//        private Integer climb;
//        private Integer fly;
//        private Integer hover;
//        private Integer swim;
//    }


    public static void main(String[] args) {
        String testDataPath = "C:/Users/IFlyts/Downloads/mini_bestiary.csv";
        List<Enemy> enemies;
        EnemyCsvParserService parser = new EnemyCsvParserService();
        try {
            enemies = parser.parseCsv(testDataPath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        for (Enemy e : enemies) {
            System.out.println(e.name + ": " + e.type + " - " + e.ac);
        }
    }
}
