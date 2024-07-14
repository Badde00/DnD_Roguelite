package com.dnd.rougelite.Datastructures;

import java.util.ArrayList;

public class JsonItemWrapper {
  private ArrayList<String> items;
  private ArrayList<Float> weights;

  public ArrayList<String> getItems() {
    return this.items;
  }

  public ArrayList<Float> getWeights() {
    return this.weights;
  }

  public void setItems(ArrayList<String> items) {
    this.items = items;
  }

  public void setWeights(ArrayList<Float> weights) {
    this.weights = weights;
  }
}
