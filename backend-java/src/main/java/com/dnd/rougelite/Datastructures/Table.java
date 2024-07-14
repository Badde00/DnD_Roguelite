package com.dnd.rougelite.Datastructures;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Table {
  private String tableName;
  private Path jsonFilePath;
  private ArrayList<String> data;

  public Table(String tableName, String jsonFilePath) {
    this.tableName = tableName;
    this.jsonFilePath = Paths.get(jsonFilePath);
    this.data = new ArrayList<String>();
    try {
      String jsonString = Files.readString(this.jsonFilePath, StandardCharsets.UTF_8);
      ObjectMapper mapper = new ObjectMapper();
      this.data = mapper.readValue(jsonString, new TypeReference<ArrayList<String>>() {});
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String toString() {
    return this.tableName + " : " + this.data.toString();
  }

  public String rollOnTable() {
    int randomIndex = (int) (Math.random() * this.data.size());
    return this.data.get(randomIndex);
  }

  public String getTableName() {
    return this.tableName;
  }

  public Path getJsonFilePath() {
    return this.jsonFilePath;
  }

  public ArrayList<String> getData() {
    return this.data;
  }
}