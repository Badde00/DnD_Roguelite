package com.dnd.rougelite.Datastructures;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Table {
  protected String tableName;
  protected Path jsonFilePath;
  protected ArrayList<String> data;

  public Table(String tableName, String jsonFilePath) {
    this.tableName = tableName;
    this.jsonFilePath = Paths.get(jsonFilePath);
    this.data = new ArrayList<String>();
    try {
      String jsonString = Files.readString(this.jsonFilePath, StandardCharsets.UTF_8);
      ObjectMapper mapper = new ObjectMapper();
      JsonItemWrapper wrapper = mapper.readValue(jsonString, JsonItemWrapper.class);
      this.data = wrapper.getItems();
    } catch (NoSuchFileException e) {
      System.out.println("File not found: " + this.jsonFilePath.toString());
      System.out.println("CWD: " + System.getProperty("user.dir"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String rollOnTableRange(int start, int end) throws IllegalArgumentException {
    if (start < 0 || end >= this.data.size()) {
      throw new IllegalArgumentException("Invalid range.");
    }

    int randomIndex = (int) (Math.random() * (end - start + 1)) + start;
    return this.data.get(randomIndex);
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

  public static void main(String[] args) {
    Table table = new Table("test", "test.json");
    System.out.println(table.rollOnTable());
  }
}