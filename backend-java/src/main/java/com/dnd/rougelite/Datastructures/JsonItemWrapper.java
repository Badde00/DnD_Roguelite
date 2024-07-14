package com.dnd.rougelite.Datastructures;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

public class JsonItemWrapper {
  @Getter @Setter private ArrayList<String> items;
  @Getter @Setter private ArrayList<Float> weights;
}
