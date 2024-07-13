import random


class Table:
  def __init__(self, name, items):
    self.name = name
    self.items = items
  
  def __str__(self):
    return self.name + ": " + str(self.items)
  
  def roll(self):
    return random.choice(self.items)


class TableWeighted(Table):
  def __init__(self, name, items, weights):
    super().__init__(name, items)
    self.weights = weights

  def roll(self):
    return random.choices(self.items, weights=self.weights, k=1)[0]


class RangeTable(Table):
  def __init__(self, name, items):
    super().__init__(name, items)

  def rollRange(self, start, end):
    if start < 0 or end >= len(self.items) or start > end:
      raise ValueError(f"Invalid range: {start}-{end}")

    return random.choice(self.items[start:end+1])


currency = Table("Currency", ["Gold", "Electrum", "Gemstones", "Faith", "Artwork"])
combatReward = TableWeighted("Combat Reward", ["Currency", "Upgrade Weapon/Health", "Upgrade Armor", "Upgrade Class", "Patronage", "Feat"],
                             [0.5, 0.1, 0.1, 0.1, 0.1, 0.1])
deal = Table("Deal", ["Devil", "Fae", "Giant", "Dragon", "Aberration", "Faction: Knights", "Faction: Mage", "Faction: Thieves"])
combatVariation = Table("Combat Variation", ["Modified Opponents", "Mixed Opponents", "Traps", "Survival", "Rival Duel", "Rival Brawl"])
encounter = TableWeighted("Encounter", ["Standard Combat", "Variant Combat", "Shop", "Rest", "Gambling Room", "Social", "Puzzle", "Treasure Room", "Deal"],
                          [0.4, 0.1, 0.1, 0.1, 0.08, 0.08, 0.08, 0.04, 0.02])
difficulty = RangeTable("Difficulty", ["Easy", "Medium", "Hard", "Deadly"])
lowTierTheme = Table("Low-Tier Theme", ["Beast", "Construct", "Humanoid", "Monstrosity", "Undead"])
midTierTheme = Table("Mid-Tier Theme", ["Aberration", "Beast", "Construct", "Dragon", "Elemental", "Fey", "Fiend", "Giant", "Humanoid", "Monstrosity", "Undead"])
highTierTheme = Table("High-Tier Theme", ["Aberration", "Celestial", "Construct", "Dragon", "Elemental", "Fiend", "Giant", "Humanoid", "Monstrosity", "Undead"])
endGameTheme = Table("End-Game Theme", ["Aberration", "Construct", "Dragon", "Fiend", "Giant", "Monstrosity", "Undead"])

# XP Thresholds by Character Level (Easy, Medium, Hard, Deadly) for 1 player
xpThresholdsByLevel = [ (25, 50, 75, 100), (50, 100, 150, 200), (75, 150, 225, 400), (125, 250, 375, 500),
  (250, 500, 750, 1100), (300, 600, 900, 1400), (350, 750, 1100, 1700), (450, 900, 1400, 2100),
  (550, 1100, 1600, 2400), (600, 1200, 1900, 2800), (800, 1600, 2400, 3600), (1000, 2000, 3000, 4500),
  (1100, 2200, 3400, 5100), (1250, 2500, 3800, 5700), (1400, 2800, 4300, 6400), (1600, 3200, 4800, 7200),
  (2000, 3900, 5900, 8800), (2100, 4200, 6300, 9500), (2400, 4900, 7300, 10900), (2800, 5700, 8500, 12700)
]

def getPartyXpThresholds(level, difficulty="medium"):
  easy, medium, hard, deadly = xpThresholdsByLevel[level - 1]
  difficultyTable = {
    "easy": easy * 4,
    "medium": medium * 4,
    "hard": hard * 4,
    "deadly": deadly * 4
  }
  return difficultyTable[difficulty]

# XP values for different challenge ratings (CR)
xpByCR = {
  0: 10, 1/8: 25, 1/4: 50, 1/2: 100, 1: 200, 2: 450, 3: 700, 4: 1100, 5: 1800,
  6: 2300, 7: 2900, 8: 3900, 9: 5000, 10: 5900, 11: 7200, 12: 8400, 13: 10000,
  14: 11500, 15: 13000, 16: 15000, 17: 18000, 18: 20000, 19: 22000, 20: 25000,
  21: 33000, 22: 41000, 23: 50000, 24: 62000, 25: 75000, 26: 90000, 27: 105000,
  28: 120000, 29: 135000, 30: 155000
}

# Encounter Multipliers
encounterMultipliers = [ (1, 1), (2, 1.5), (3, 2), (7, 2.5), (11, 3), (15, 4) ]
