package com.dnd.rougelite.Datastructures;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class CrDeserializer extends JsonDeserializer<String> {
  @Override
  public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    JsonNode node = p.getCodec().readTree(p);
    if (node.isTextual()) {
      return node.asText();
    } else if (node.isObject()) {
      JsonNode crNode = node.get("cr");
      if (crNode != null && crNode.isTextual()) {
        return crNode.asText();
      }
    }
    return null;
  }
  
}
