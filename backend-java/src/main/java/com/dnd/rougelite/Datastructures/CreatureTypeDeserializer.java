package com.dnd.rougelite.Datastructures;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class CreatureTypeDeserializer extends JsonDeserializer<String> {
  @Override
  public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
    JsonNode node = p.getCodec().readTree(p);
    if (node.isTextual()) {
      return node.asText();
    } else if (node.isObject()) {
      JsonNode typeNode = node.get("type");
      if (typeNode != null && typeNode.isTextual()) {
        return typeNode.asText();
      }
    }
    return null;
  }
}
