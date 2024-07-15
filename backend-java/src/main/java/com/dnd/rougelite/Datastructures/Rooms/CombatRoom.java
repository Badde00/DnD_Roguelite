package com.dnd.rougelite.Datastructures.Rooms;

import java.util.List;

import com.dnd.rougelite.Datastructures.CreatureWrapper;
import com.dnd.rougelite.Datastructures.Enums.CombatReward;
import com.dnd.rougelite.Datastructures.Enums.CombatTheme;
import com.dnd.rougelite.Datastructures.Enums.Difficulty;

public class CombatRoom extends Room {
  private List<CreatureWrapper> enemies;
  private CombatTheme theme;
  private Difficulty difficulty;
  private CombatReward rewardType;
  private int rewardAmount;
  private int goldReward;

  public CombatRoom(int groupLevel, List<CreatureWrapper> enemies, CombatTheme theme, Difficulty difficulty, CombatReward rewardType, int rewardAmount, int goldReward) {
    super(groupLevel);
    this.enemies = enemies;
    this.theme = theme;
    this.difficulty = difficulty;
    this.rewardType = rewardType;
    this.rewardAmount = rewardAmount;
    this.goldReward = goldReward;
  }
}
