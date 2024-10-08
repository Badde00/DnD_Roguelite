package com.palmstam.roguelite.model.data;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntriesDeserializer extends JsonDeserializer<List<Object>> {
    @Override
    public List<Object> deserialize(JsonParser p, DeserializationContext context) throws IOException {
        List<Object> descriptions = new ArrayList<>();
        JsonNode node = p.getCodec().readTree(p);

        if (node.isArray()) {
            for (JsonNode entryNode : node) {
                if (entryNode.isTextual()) {
                    descriptions.add(entryNode.asText()); //Basic strings
                } else  if (entryNode.has("entries")) {
                    JsonNode innerEntries = entryNode.get("entries");
                    if (innerEntries.isArray()) {
                        innerEntries.forEach(subEntry -> {
                            if (subEntry.isTextual()) {
                                descriptions.add(subEntry.asText()); //Strings inside objects
                            }
                        });
                    }
                }
            }
        }
        return descriptions;
    }
}
