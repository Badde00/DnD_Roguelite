package com.dnd.rougelite.Datastructures;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TableWeighted extends Table {
  private ArrayList<Float> weights;

  public TableWeighted(String tableName, String jsonFilePath) throws IllegalArgumentException {
    super(tableName, jsonFilePath);
    try {
      String jsonSTring = Files.readString(this.jsonFilePath, StandardCharsets.UTF_8);
      ObjectMapper mapper = new ObjectMapper();
      JsonItemWrapper wrapper = mapper.readValue(jsonSTring, JsonItemWrapper.class);
      this.weights = wrapper.getWeights();
      if (this.weights == null) {
        throw new IllegalArgumentException("Weights must be provided for a weighted table.");
      }
      validateWeights(this.weights);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public TableWeighted(Table table, ArrayList<Float> weights) throws IllegalArgumentException {
    super(table.getTableName(), table.getJsonFilePath().toString());
    validateWeights(weights);
    this.weights = weights;
  }

  private void validateWeights(ArrayList<Float> weights) throws IllegalArgumentException {
    if (weights.size() != this.getData().size()) {
      throw new IllegalArgumentException("Weights must be the same size as the data array.");
    }

    float sum = 0;
    for (float weight : weights) {
      sum += weight;
    }

    if (sum != 1.0f) {
      throw new IllegalArgumentException("Weights must sum to 1.");
    }
  }

  @Override
  public String rollOnTable() {
    double random = Math.random();
    double cumulativeProbability = 0.0;
    for (int i = 0; i < this.getData().size(); i++) {
      cumulativeProbability += this.weights.get(i);
      if (random < cumulativeProbability) {
        return getData().get(i);
      }
    }

    return getData().get(getData().size() - 1);
  }
}
