import json
import random


class JSONHandler:
  def __init__(self, file_path):
    with open(file_path, 'r') as file:
      self.data = json.load(file)

  def generateCreaturesByCRAndType(self, typeList, crList):
    if isinstance(typeList, str):
      typeList = [typeList]
    for i in range(len(crList)):
      if isinstance(crList[i], float):
        if crList[i] == 0.125:
          crList[i] = "1/8"
        elif crList[i] == 0.25:
          crList[i] = "1/4"
        elif crList[i] == 0.5:
          crList[i] = "1/2"
      else:
        crList[i] = str(crList[i])
    generatedCreatures = []
    for cr in crList:
      filteredCreatures = [creature for creature in self.data if creature['cr'] == cr and creature['type'] in typeList]
      if filteredCreatures:
        selectedCreature = random.choice(filteredCreatures)
        generatedCreatures.append(selectedCreature)
    return generatedCreatures


  def getCreatureNames(self, jsonList):
    return [creature['name'] for creature in jsonList]
