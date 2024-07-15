package com.dnd.rougelite.Datastructures.Enums;

import java.util.HashMap;
import java.util.Map;

public class EncounterMapper {
  private static final Map<String, EncounterType>stringToEncounterTypeMap = new HashMap<>();
  private static final Map<EncounterType, String> encounterTypeToStringMap = new HashMap<>();

  static {
    stringToEncounterTypeMap.put("Standard Combat", EncounterType.STANDARD_COMBAT);
    stringToEncounterTypeMap.put("Variant Combat", EncounterType.VARIATION_COMBAT);
    stringToEncounterTypeMap.put("Shop", EncounterType.SHOP);
    stringToEncounterTypeMap.put("Rest", EncounterType.REST);
    stringToEncounterTypeMap.put("Gambling", EncounterType.GAMBLING);
    stringToEncounterTypeMap.put("Social", EncounterType.SOCIAL);
    stringToEncounterTypeMap.put("Puzzle", EncounterType.PUZZLE);
    stringToEncounterTypeMap.put("Treasure", EncounterType.TREASURE);
    stringToEncounterTypeMap.put("Deal", EncounterType.DEAL);

    for (Map.Entry<String, EncounterType> entry : stringToEncounterTypeMap.entrySet()) {
      encounterTypeToStringMap.put(entry.getValue(), entry.getKey());
    }
  }

  public static EncounterType toEncounterType(String encounterType) {
    return stringToEncounterTypeMap.get(encounterType);
  }

  public static String toString(EncounterType encounterType) {
    return encounterTypeToStringMap.get(encounterType);
  }
}
