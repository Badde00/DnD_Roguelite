package com.dnd.rougelite.Datastructures;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatureWrapper {
  @Getter
  @Setter
  private String name;

  @Getter
  @Setter
  private String source;

  @Getter
  @Setter
  @JsonDeserialize(using = CreatureTypeDeserializer.class)
  private String type;

  @Getter
  @Setter
  @JsonDeserialize(using = CrDeserializer.class)
  private String cr;
}
