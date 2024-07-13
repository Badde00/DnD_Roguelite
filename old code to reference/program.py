import tables
import random
from jsonCreatureSelector import JSONHandler


hiddenProbability = 1/6
extraOptionProbability = 1/2
latestGeneratedEnemies = []
creatureHandler = JSONHandler("all_creatures.json")

difficultyMapping = {
  "Currency": (0, 2),        # easy-hard
  "Upgrade Armor": (1, 3),   # medium-deadly
  "Upgrade Weapon/Health": (1, 2),  # easy-hard
  "Upgrade Class": (2, 3),  # hard-deadly
  "Patronage": (1, 2),       # medium-hard
  "Feat": (0, 3)             # easy-deadly
}


def generateEnconters():
  sequence = []
  for _ in range(3):
    sequence.append(generateOptions())

  sequence.append("Mini Boss Room")

  for _ in range(3):
    sequence.append(generateOptions())

  sequence.append("Choose from Rest, Shop" + ("" if random.random() < extraOptionProbability else ", " + tables.combatReward.roll()))  # Rest or Shop

  sequence.append("Boss Room")

  return sequence


def formatCombat(option):
  if option != "Standard Combat" and option != "Variant Combat":
    return option
  
  combatReward = tables.combatReward.roll()  # Decide combat reward
  if option == "Variant Combat" and combatReward == "Currency":  # Variant combat can't have currency
    while combatReward == "Currency":
      combatReward = tables.combatReward.roll()

  start, end = difficultyMapping[combatReward]  # Difficulty mapping
  difficulty = tables.difficulty.rollRange(start, end)  # Roll for difficulty

  if combatReward == "Currency":  # If currency, roll for specific currency
    combatReward = tables.currency.roll()

  hidden = random.random() < hiddenProbability  # 1/6 chance to hide the reward
  if hidden:
    combatReward = f"hidden:{combatReward}"

  combatVariation = ", " + tables.combatVariation.roll() if option == "Variant Combat" else ""  # Roll for combat variation
  option = f"{option} ({combatReward}, {difficulty}{combatVariation})"  # Combine all the information

  return option


def generateOptions():
  options = []
  base_options = set()

  while len(options) < 2: # Generate a minimum of 2 options
    option = tables.encounter.roll()

    if option == "Standard Combat" or option == "Variant Combat": # If combat, additional infomation needs to be added
      formatted_option = formatCombat(option)

      if option not in base_options: # Check if the option has already been added
        options.append(formatted_option)
        base_options.add(option)
    elif option not in base_options:
      options.append(option)
      base_options.add(option)

  if random.random() < extraOptionProbability:  # 50% chance to add a third option
    while len(options) < 3: # Keep generating until a unique option is found
      option = tables.encounter.roll()
      if option == "Standard Combat" or option == "Variant Combat": # If combat, additional infomation needs to be added
        formatted_option = formatCombat(option)

        if option not in base_options: # Check if the option has already been added
          options.append(formatted_option)
          base_options.add(option)
      elif option not in base_options:
        options.append(option)
        base_options.add(option)

  return options


def displayEncounters(encounters):
  for i, encounter in enumerate(encounters):
    if isinstance(encounter, list):
      print(f"Encounter {i+1}: Choose from {', '.join(encounter)}")
    else:
      print(f"Encounter {i+1}: {encounter}")


def rollTheme(level):
  theme = ""
  if level <= 3:
    theme = tables.lowTierTheme.roll()
  elif level <= 6:
    theme = tables.midTierTheme.roll()
  elif level <= 11:
    theme = tables.highTierTheme.roll()
  else:
    theme = tables.highTierTheme.roll()

  return theme


def rollDice(number, sides):
  return sum([random.randint(1, sides) for _ in range(number)])


def rollGold(level, isGoldRoom=False, isCurrencyRoom=False):
  goldPerPerson = 0
  if 1 <= level <= 5:
    goldPerPerson = rollDice(1, 10) * 5 * 2 ** (level - 1)
  elif 6 <= level <= 10:
    if level % 2 == 1:
      level -= 1
    goldPerPerson = 50 * level + 100 * rollDice(1, level)
  elif 11 <= level <= 15:
    goldPerPerson = 100 * level + 50 * rollDice(4, 10)
  else:
    goldPerPerson = 500 * level + 1000 * rollDice(1, 20)
  
  if isGoldRoom:
    goldPerPerson *= 2
  if isCurrencyRoom:
    goldPerPerson *= 2

  return goldPerPerson


def displayCombatInformation():
  print("What is the group level?")
  level = ""
  while level == "":
    level = input("Enter the group level: ")
    if not level.isdigit() or int(level) < 1 or int(level) > 20:
      print("Invalid level. Enter a number between 1 and 20.")
    else:
      level = int(level)

  theme = rollTheme(level)

  print("Is it a currency room?")
  isCurrencyRoom = input("Enter 'y' for yes, 'n' for no: ") == "y"
  isGoldRoom = False
  if isCurrencyRoom:
    print("Is the currency room a gold room?")
    isGoldRoom = input("Enter 'y' for yes, 'n' for no: ") == "y"
  goldPerPerson = rollGold(level, isGoldRoom, isCurrencyRoom)

  print("What is the difficulty?")
  difficulty = input("Enter 'easy', 'medium', 'hard', or 'deadly': ")
  enemies = generateEnemies(level, difficulty)
  global latestGeneratedEnemies
  latestGeneratedEnemies = enemies

  print(f"The room will be a {theme} room with {goldPerPerson} gold per person as a gold reward.")
  print(f"With a difficulty of {difficulty}, the enemies will have the CRs {enemies} with a total XP of {calculateEncounterXp(enemies, tables.xpByCR, tables.encounterMultipliers)}.")


def findMultiplier(n, encounterMultipliers=tables.encounterMultipliers):
  return max(value for key, value in encounterMultipliers if n >= key)

def calculateEncounterXp(enemies, xpByCR, encounterMultipliers):
  count = len(enemies)
  totalXp = sum(xpByCR[cr] for cr in enemies)
  multiplier = findMultiplier(count, encounterMultipliers)
  return totalXp * multiplier


def generateEnemies(level, difficulty):
  enemies = []
  maxXp = tables.getPartyXpThresholds(level, difficulty)
  xpByCR = tables.xpByCR
  unadjustedXpSum = 0

  while unadjustedXpSum * findMultiplier(len(enemies) if len(enemies) > 0 else 1) < maxXp * 0.9: # Doesn't need to be exact
    multiplier = findMultiplier(len(enemies) + 1)
    possibleCrs = [cr for cr, xp in xpByCR.items() if xp * multiplier <= maxXp - unadjustedXpSum * multiplier]
    if not possibleCrs:
      break
    cr = random.choice(possibleCrs)
    enemies.append(cr)
    unadjustedXpSum += xpByCR[cr]

  return sorted(enemies, reverse=True)


def generateCombatEncounter():
  typeList = []
  print("What type of enemies would you like to generate?")
  answer = ""
  while answer != "done":
    answer = input("Enter a type of enemy or 'done' to finish: ")
    if answer != "done":
      typeList.append(answer)
  output = creatureHandler.getCreatureNames(creatureHandler.generateCreaturesByCRAndType(typeList, latestGeneratedEnemies))
  print(f"The generated enemies are: {', '.join(output)}")
  return output



def main():
  while True:
    print("\n\nWhat would you like to do?")
    print("1. Generate rooms")
    print("2. Generate combat infomation")
    print("3. Generate combat encounter with latest generated enemies")

    choice = input("Enter your choice: ")

    if choice == "1":
      encounters = generateEnconters()
      displayEncounters(encounters)
    elif choice == "2":
      displayCombatInformation()
    elif choice == "3":
      if not latestGeneratedEnemies:
        print("No enemies have been generated yet.")
      else:
        generateCombatEncounter()
    else:
      print("Invalid choice")


if __name__ == "__main__":
  main()
