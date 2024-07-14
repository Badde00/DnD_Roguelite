package com.dnd.rougelite;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.dnd.rougelite.Datastructures.CreatureWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CreatureSelector {
  private List<CreatureWrapper> data;

  public CreatureSelector(String filePath) {
    Path path = Paths.get(filePath);
    String jsonString = Files.readString(path, StandardCharsets.UTF_8);
    ObjectMapper mapper = new ObjectMapper();
    this.data = mapper.readValue(jsonString, new TypeReference<List<CreatureWrapper>>() {});
  }

  public List<CreatureWrapper> generateCreaturesByCRAndType(List<String> typeList, List<Object> crList) {
    // TODO: Implement this method
  }
}
