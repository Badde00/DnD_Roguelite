package com.palmstam.roguelite.model.databaseItems;

import com.palmstam.roguelite.model.data.EnemyCsvParserService;
import com.palmstam.roguelite.model.data.EnemyDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "enemies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Enemy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    private String source;

    private int page;

    private String size;

    @Embedded
    private Type type;

    private String alignment;

    @Embedded
    private Ac ac;

    @Embedded
    private Hp hp;

    @Embedded
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
    @Embeddable
    @NoArgsConstructor
    public class Type {
        // Input will be things like "Humanoid (Dwarf)", "Fiend (Demon)" or "Beast"
        private String primaryType; // Something like "Humanoid" or "Fiend"
        private String subType; // Something like "Dwarf" or "Demon"

        public Type(String typeString) {
            setType(typeString);
        }

        public String toString() {
            if (subType.isBlank()) {
                return primaryType;
            } else {
                return String.format("%s (%s)", primaryType, subType);
            }
        }

        public void setType(String typeString) {
            if (typeString.contains("(")) {
                String[] parts = typeString.split("\\(");
                this.primaryType = parts[0].trim();
                this.subType = parts[1].replace(")", "");
            } else {
                this.primaryType = typeString.trim();
                this.subType = "";
            }
        }
    }


    @Getter
    @AllArgsConstructor
    @Embeddable
    public class Ac {
        // Input will be things like "18 (natural armor)" or "18 (natural armor)"
        private int ac;
        private String source;

        public Ac(String acString) {
            setAc(acString);
        }

        public String toString() {
            if (source.isBlank()) {
                return String.valueOf(ac);
            } else {
                return String.format("%d (%s)", ac, source);
            }
        }

        public void setAc(String acString) {
            if (acString.contains("(")) {
                String[] parts = acString.split("\\(");
                this.ac = Integer.parseInt(parts[0].trim());
                this.source = parts[1].replace(")", "");
            } else {
                this.ac = Integer.parseInt(acString.trim());
                this.source = "";
            }
        }
    }

    @Getter
    @AllArgsConstructor
    @Embeddable
    public class Hp {
        private int hp;
        private String calculation;

        public Hp(String hpString) {
            setHp(hpString);
        }

        public String toString() {
            return String.format("%d (%s)", hp, calculation);
        }

        public void setHp(String hpString) {
            if (hpString.contains("(")) {
                String[] parts = hpString.split("\\(");
                this.hp = Integer.parseInt(parts[0].trim());
                this.calculation = parts[1].replace(")", "");
            } else {
                this.hp = Integer.parseInt(hpString.trim());
            }
        }
    }

    @Getter
    @Embeddable
    public static class Speed {
        private Integer walk = 0;
        private Integer burrow = 0;
        private Integer climb = 0;
        private Integer fly = 0;
        private Integer hover = 0;
        private Integer swim = 0;

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();

            sb.append(walk).append(" ft.");
            if (climb != 0) appendWithComma(sb, "Climb: " + climb + " ft.");
            if (burrow != 0) appendWithComma(sb, "Burrow: " + burrow + " ft.");
            if (hover != 0) {
                appendWithComma(sb, "Fly: " + hover + " ft. (hover)");
            } else if (fly != 0) {
                appendWithComma(sb, "Fly: " + fly + " ft.");
            }
            if (hover != 0) appendWithComma(sb, "Hover: " + hover + " ft.");
            if (swim != 0) appendWithComma(sb, "Swim: " + swim + " ft.");

            return sb.toString();
        }

        public Speed(String speedString) {
            if (speedString == null || speedString.isBlank()) {
                return;
            }

            String[] parts = speedString.split(",");

            for (String part : parts) {
                part = part.trim();

                if (part.contains("climb")) {
                    this.climb = parseSpeed(part);
                } else if (part.contains("burrow")) {
                    this.burrow = parseSpeed(part);
                } else if (part.contains("swim")) {
                    this.swim = parseSpeed(part);
                } else if (part.contains("fly")) {
                    if (part.contains("hover")) {
                        this.hover = parseSpeed(part);
                        this.fly = 0; // Hover overwrites fly
                    } else {
                        this.fly = parseSpeed(part);
                    }
                } else if (part.contains("hover")) {
                    this.hover = parseSpeed(part);
                } else {
                    // If no specific type, assume it's walking
                    this.walk = parseSpeed(part);
                }
            }
        }

        private Integer parseSpeed(String speed) {
            Matcher matcher = Pattern.compile("(\\d+)\\s*ft\\.").matcher(speed);
            if (matcher.find()) {
                return Integer.parseInt(matcher.group(1));
            }
            return 0;
        }

        private void appendWithComma(StringBuilder sb, String value) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(value);
        }
    }

    public Enemy(EnemyDTO edto) {
        Type type = new Type(edto.getType());
        Ac ac = new Ac(edto.getAc());
        Hp hp = new Hp(edto.getHp());
        Speed speed = new Speed(edto.getSpeed());
        this.name = edto.getName();
        this.source = edto.getSource();
        this.page = edto.getPage();
        this.size = edto.getSize();
        this.type = type;
        this.alignment = edto.getAlignment();
        this.ac = ac;
        this.hp = hp;
        this.speed = speed;
        this.strength = edto.getStrength();
        this.dexterity = edto.getDexterity();
        this.constitution = edto.getConstitution();
        this.intelligence = edto.getIntelligence();
        this.wisdom = edto.getWisdom();
        this.charisma = edto.getCharisma();
        this.savingThrows = edto.getSavingThrows();
        this.skills = edto.getSkills();
        this.damageVulnerabilities = edto.getDamageVulnerabilities();
        this.damageResistances = edto.getDamageResistances();
        this.damageImmunities = edto.getDamageImmunities();
        this.conditionImmunities = edto.getConditionImmunities();
        this.senses = edto.getSenses();
        this.languages = edto.getLanguages();
        this.cr = edto.getCr();
        this.traits = edto.getTraits();
        this.actions = edto.getActions();
        this.bonusActions = edto.getBonusActions();
        this.reactions = edto.getReactions();
        this.legendaryActions = edto.getLegendaryActions();
        this.mythicActions = edto.getMythicActions();
        this.lairActions = edto.getLairActions();
        this.regionalEffects = edto.getRegionalEffects();
        this.environment = edto.getEnvironment();
    }


//    public static void main(String[] args) {
//        String testDataPath = "C:/Users/IFlyts/Downloads/Bestiary.csv";
//        List<EnemyDTO> dtoEnemies;
//        List<Enemy> enemies = new ArrayList<>();
//        EnemyCsvParserService parser = new EnemyCsvParserService();
//        try {
//            dtoEnemies = parser.parseCsv(testDataPath);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        for (EnemyDTO edto : dtoEnemies) {
//            enemies.add(new Enemy(edto));
//        }
//        for (Enemy e : enemies) {
//            System.out.println(e.name + " - " + e.speed + " - " + e.type + " - " + e.type.primaryType);
//        }
//    }
}
