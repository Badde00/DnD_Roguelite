package com.dnd.rougelite.Datastructures;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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
    } catch (IOException e) {
      // Handle the exception here
      e.printStackTrace();
    }
  }


}