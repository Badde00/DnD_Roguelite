package com.palmstam.roguelite.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.AbstractBeanField;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import com.palmstam.roguelite.model.databaseItems.Enemy;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EnemyDTO {
    @CsvBindByName(column = "Name")
    private String name;

    @CsvBindByName(column = "Source")
    private String source;

    @CsvCustomBindByName(column = "Page", converter = PageConverter.class)
    private int page;

    @CsvBindByName(column = "Size")
    private String size;

    @CsvBindByName(column = "Type")
    private String type;

    @CsvBindByName(column = "Alignment")
    private String alignment;

    @CsvBindByName(column = "AC")
    private String ac;

    @CsvBindByName(column = "HP")
    private String hp;

    @CsvBindByName(column = "Speed")
    private String speed;

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

    public static class PageConverter extends AbstractBeanField<Integer, String> {
        @Override
        protected Integer convert(String value) {
            if ("undefined".equalsIgnoreCase(value)) {
                return -1;
            }

            try {
                return Integer.parseInt(value.trim());
            } catch (NumberFormatException e) {
                return -1;
            }
        }
    }


//    public static void main(String[] args) {
//        String testDataPath = "C:/Users/IFlyts/Downloads/Bestiary.csv";
//        List<EnemyDTO> enemies;
//        EnemyCsvParserService parser = new EnemyCsvParserService();
//        try {
//            enemies = parser.parseCsv(testDataPath);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        for (EnemyDTO e : enemies) {
//            System.out.println(e.name + ": " + e.speed);
//        }
//    }
}
