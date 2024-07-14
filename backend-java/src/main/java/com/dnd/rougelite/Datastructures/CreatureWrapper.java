package com.dnd.rougelite.Datastructures;

import lombok.Getter;
import lombok.Setter;

public class CreatureWrapper {
  @Getter @Setter private String name;
  @Getter @Setter private String source;
  @Getter @Setter private String type;
  @Getter @Setter private int ac;
  @Getter @Setter private int hp;
  @Getter @Setter private float cr;
}
