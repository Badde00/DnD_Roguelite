package com.dnd.rougelite;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.dnd.rougelite.Datastructures.CreatureWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreatureSelector {
  private List<CreatureWrapper> data;

  public CreatureSelector(String filePath) {
    Path path = Paths.get(filePath);
    try {
      String jsonString = Files.readString(path, StandardCharsets.UTF_8);
      ObjectMapper mapper = new ObjectMapper();
      List<CreatureWrapper> temp = mapper.readValue(jsonString, new TypeReference<List<CreatureWrapper>>() {
      });
      this.data = temp;
    } catch (NoSuchFileException e) {
      System.out.println("File not found: " + filePath.toString());
      System.out.println("CWD: " + System.getProperty("user.dir"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public List<CreatureWrapper> generateCreaturesByCrAndType(List<String> typeList, List<String> crList) {
    // Example input typeList: ["beast", "monstrosity"]
    // Example input crList: ["1/4", "1/2", "1", "2", "1/4"]

    Map<String, List<CreatureWrapper>> creaturesByCrAndType = new HashMap<>();

    for (CreatureWrapper creature : data) {
      if (creature.getType() != null && typeList.contains(creature.getType())) {
        String key = creature.getCr() + "-" + creature.getType();
        creaturesByCrAndType.computeIfAbsent(key, k -> new ArrayList<>()).add(creature);
      }
    }

    List<CreatureWrapper> outputList = new ArrayList<>();
    Random random = new Random();
    for (String cr : crList) {
      List<CreatureWrapper> possibleCreatures = new ArrayList<>();
      for (String type : typeList) {
        List<CreatureWrapper> creatures = creaturesByCrAndType.get(cr + "-" + type);
        if (creatures != null) {
          possibleCreatures.addAll(creatures);
        }
      }
      if (!possibleCreatures.isEmpty()) {
        int randomIndex = random.nextInt(possibleCreatures.size());
        outputList.add(possibleCreatures.get(randomIndex));
      }
    }

    return outputList;
  }
}
