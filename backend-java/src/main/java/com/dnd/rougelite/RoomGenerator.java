package com.dnd.rougelite;

import java.util.ArrayList;

import com.dnd.rougelite.Datastructures.Table;
import com.dnd.rougelite.Datastructures.TableWeighted;
import com.dnd.rougelite.Datastructures.Enums.CombatReward;
import com.dnd.rougelite.Datastructures.Enums.CombatTheme;
import com.dnd.rougelite.Datastructures.Enums.Difficulty;
import com.dnd.rougelite.Datastructures.Enums.EncounterType;
import com.dnd.rougelite.Datastructures.Rooms.Room;

public class RoomGenerator {
  private Table encounterTable;

  public RoomGenerator() {
    String encounterPath = "backend-java\\src\\main\\java\\com\\dnd\\rougelite\\json\\encounter.json";
    this.encounterTable = new TableWeighted("EncounterTable", encounterPath);
  }

  public ArrayList<Room[]> generateRoomProgression(int groupLevel, float hiddenProbability, float extraOptionProbability) {
    ArrayList<Room[]> rooms = new ArrayList<Room[]>();

    for (int i = 0; i < 3; i++) {
      RoomInformationBuilder builder = new RoomInformationBuilder(groupLevel);
      Room room = generateRoom(builder);
    }
    
    
    return null;
  }

  public Room generateRoom(RoomInformationBuilder builder) {
    EncounterType encounterType = builder.encounterType;
    if (encounterType == null) {
      encounterType = (EncounterType) encounterTable.rollOnTable();
    }



    return null;
  }


  public static class RoomInformationBuilder {
    // Required parameter
    private int groupLevel;

    // Optional parameters
    private EncounterType encounterType;
    private CombatTheme combatTheme;
    private Difficulty difficulty;
    private ArrayList<String> creatures;
    private CombatReward combatReward;
    private int currencyAmount;
    private int goldReward;
    private String dealType;
    private String combatVariation;
    private String shopInventory;
    private String gamblingGame;
    private String socialDescription;
    private String puzzleDescription;
    private String treasure;

    public RoomInformationBuilder(int groupLevel) {
      this.groupLevel = groupLevel;
    }

    public RoomInformationBuilder groupLevel (int groupLevel) {
      this.groupLevel = groupLevel;
      return this;
    }

    public RoomInformationBuilder encounterType(EncounterType encounterType) {
      this.encounterType = encounterType;
      return this;
    }

    public RoomInformationBuilder combatTheme(CombatTheme combatTheme) {
      this.combatTheme = combatTheme;
      return this;
    }

    public RoomInformationBuilder difficulty(Difficulty difficulty) {
      this.difficulty = difficulty;
      return this;
    }

    public RoomInformationBuilder creatures(ArrayList<String> creatures) {
      this.creatures = creatures;
      return this;
    }

    public RoomInformationBuilder combatReward(CombatReward combatReward) {
      this.combatReward = combatReward;
      return this;
    }

    public RoomInformationBuilder currencyAmount(int currencyAmount) {
      this.currencyAmount = currencyAmount;
      return this;
    }

    public RoomInformationBuilder goldReward(int goldReward) {
      this.goldReward = goldReward;
      return this;
    }

    public RoomInformationBuilder dealType(String dealType) {
      this.dealType = dealType;
      return this;
    }

    public RoomInformationBuilder combatVariation(String combatVariation) {
      this.combatVariation = combatVariation;
      return this;
    }

    public RoomInformationBuilder shopInventory(String shopInventory) {
      this.shopInventory = shopInventory;
      return this;
    }

    public RoomInformationBuilder gamblingGame(String gamblingGame) {
      this.gamblingGame = gamblingGame;
      return this;
    }

    public RoomInformationBuilder socialDescription(String socialDescription) {
      this.socialDescription = socialDescription;
      return this;
    }

    public RoomInformationBuilder puzzleDescription(String puzzleDescription) {
      this.puzzleDescription = puzzleDescription;
      return this;
    }

    public RoomInformationBuilder treasure(String treasure) {
      this.treasure = treasure;
      return this;
    }
  }
}
