package com.dnd.rougelite;

import java.util.ArrayList;
import java.util.Random;

import com.dnd.rougelite.Datastructures.Table;
import com.dnd.rougelite.Datastructures.TableWeighted;
import com.dnd.rougelite.Datastructures.Enums.CombatReward;
import com.dnd.rougelite.Datastructures.Enums.CombatTheme;
import com.dnd.rougelite.Datastructures.Enums.Difficulty;
import com.dnd.rougelite.Datastructures.Enums.EncounterMapper;
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

    // Add 3 levels of 2-3 rooms
    rooms.addAll(generate3RoomLevels(groupLevel, hiddenProbability, extraOptionProbability));

    // Add miniboss room
    RoomInformationBuilder builder = new RoomInformationBuilder(groupLevel, hiddenProbability);
    builder.encounterType(EncounterType.MINIBOSS);
    rooms.add(new Room[] {generateRoom(builder)});
    // Increase level after miniboss
    builder.groupLevel(groupLevel + 1);

    // Add 3 levels of 2-3 rooms
    rooms.addAll(generate3RoomLevels(groupLevel, hiddenProbability, extraOptionProbability));

    // Add prep rooms before boss
    Room[] prepRooms = new Room[2];
    builder.encounterType(EncounterType.REST);
    prepRooms[0] = generateRoom(builder);
    builder.encounterType(EncounterType.SHOP);
    prepRooms[1] = generateRoom(builder);
    rooms.add(prepRooms);

    // Add boss room
    builder.encounterType(EncounterType.BOSS);
    rooms.add(new Room[] {generateRoom(builder)});
  }

  public ArrayList<Room[]> generate3RoomLevels(int groupLevel, float hiddenProbability, float extraOptionProbability) {
    ArrayList<Room[]> rooms = new ArrayList<Room[]>();

    for (int i = 0; i < 3; i++) {
      RoomInformationBuilder builder = new RoomInformationBuilder(groupLevel, hiddenProbability);
      
      Random random = new Random();
      boolean additionalRoom = random.nextFloat() < extraOptionProbability;
      Room[] internalRooms = new Room[additionalRoom ? 3 : 2];
      for (int j = 0; j < internalRooms.length; j++) {
        internalRooms[j] = generateRoom(builder);
      }
      rooms.add(internalRooms);
    }

    return rooms;
  }

  public Room generateRoom(RoomInformationBuilder builder) {
    EncounterType encounterType = builder.encounterType;
    if (encounterType == null) {
      encounterType = EncounterMapper.toEncounterType(encounterTable.rollOnTable());
    }




    return null;
  }


  public static class RoomInformationBuilder {
    // Required parameter
    private int groupLevel;
    private float hiddenProbability;

    // Optional parameters
    private EncounterType encounterType;
    private CombatTheme combatTheme;
    private Difficulty difficulty;
    private ArrayList<String> creatures;
    private CombatReward combatReward;
    private boolean isHidden;
    private int currencyAmount;
    private int goldReward;
    private String dealType;
    private String combatVariation;
    private String shopInventory;
    private String gamblingGame;
    private String socialDescription;
    private String puzzleDescription;
    private String treasure;

    public RoomInformationBuilder(int groupLevel, float hiddenProbability) {
      this.groupLevel = groupLevel;
      this.hiddenProbability = hiddenProbability;
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

    public RoomInformationBuilder isHidden(boolean isHidden) {
      this.isHidden = isHidden;
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
