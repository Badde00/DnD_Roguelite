package com.dnd.rougelite.Datastructures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatureWrapper {
  @Getter @Setter private String name;
  @Getter @Setter private String source;
  @Getter @Setter private String type;
  @Getter @Setter private String cr;
}
