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

    @Column(length = 512)
    private String savingThrows;

    @Column(length = 512)
    private String skills;

    @Column(length = 512)
    private String damageVulnerabilities;

    @Column(length = 512)
    private String damageResistances;

    @Column(length = 512)
    private String damageImmunities;

    @Column(length = 512)
    private String conditionImmunities;

    @Column(length = 512)
    private String senses;

    @Column(length = 512)
    private String languages;

    @Embedded
    private Cr cr;

    @Column(length = 8000)
    private String traits;

    @Column(length = 8001)
    private String actions;

    @Column(length = 2000)
    private String bonusActions;

    @Column(length = 2001)
    private String reactions;

    @Column(length = 2002)
    private String legendaryActions;

    @Column(length = 2003)
    private String mythicActions;

    @Column(length = 4000)
    private String lairActions;

    @Column(length = 4001)
    private String regionalEffects;

    @Column(length = 512)
    private String environment;


    @Getter
    @Embeddable
    @NoArgsConstructor
    public static class Type {
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
    @NoArgsConstructor
    @Embeddable
    public static class Ac {
        // Input will be things like "18 (natural armor)" or "18 (natural armor)"
        private int ac;
        private String acSource;
        private String customCaseAC = "";

        public Ac(String acString) {
            setAc(acString);
        }

        public String toString() {
            if (acSource.isBlank()) {
                return String.valueOf(ac);
            } else {
                return String.format("%d (%s)", ac, acSource);
            }
        }

        public void setAc(String acString) {
            if (acString.contains("(")) {
                String[] parts = acString.split("\\(");
                try {
                    this.ac = Integer.parseInt(parts[0].trim());
                    this.acSource = parts[1].replace(")", "");
                    this.customCaseAC = "";
                } catch (NumberFormatException e) {
                    this.ac = -1;
                    this.acSource = "";
                    this.customCaseAC = acString;
                }
            } else {
                try {
                    this.ac = Integer.parseInt(acString.trim());
                    this.acSource = "";
                    this.customCaseAC = "";
                } catch (NumberFormatException e) {
                    this.ac = -1;
                    this.acSource = "";
                    this.customCaseAC = acString;
                }
            }
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class Hp {
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
    @NoArgsConstructor
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class Cr {
        // Input will be things like "13 (10 000 XP)" or "1/4 (50 XP)"
        private String crRating;
        private String xp;

        public Cr (String crString) {
            setCr(crString);
        }

        public String toString() {
            if (xp.isBlank()) {
                return crRating;
            } else {
                return String.format("%s (%s)", crRating, xp);
            }
        }

        public void setCr(String crString) {
            if (crString.contains("(")) {
                String[] parts = crString.split("\\(");
                this.crRating = parts[0].trim();
                this.xp = parts[1].replace(")", "");
            } else {
                this.crRating = crString.trim();
                this.xp = "";
            }
        }
    }
    //{
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

    public Enemy(EnemyDTO edto) {
        Type type = new Type(edto.getType());
        Ac ac = new Ac(edto.getAc());
        Hp hp = new Hp(edto.getHp());
        Speed speed = new Speed(edto.getSpeed());
        Cr cr = new Cr(edto.getCr());
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
        this.cr = cr;
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
