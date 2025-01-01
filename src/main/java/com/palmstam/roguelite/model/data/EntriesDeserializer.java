package com.palmstam.roguelite.model.data;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EntriesDeserializer extends JsonDeserializer<List<Object>> {
    public List<Object> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        // If it's a string, return it directly
        if (node.isTextual()) {
            return List.of(node.asText());
        }

        // If it's an array, handle accordingly
        if (node.isArray()) {
            StringBuilder result = new StringBuilder();

            for (JsonNode entryNode : node) {
                // If entry is a string, append it with newline
                if (entryNode.isTextual()) {
                    result.append(entryNode.asText()).append("\n");
                } else if (entryNode.isObject()) {
                    // If entry is an object, format it as required
                    String name = entryNode.has("name") ? entryNode.get("name").asText() : "";
                    String entries = extractEntries(entryNode.get("entries"));

                    result.append("\n\n")
                            .append(name)
                            .append("\n")
                            .append(entries)
                            .append("\n\n");
                }
            }
            return List.of(result.toString().trim());
        }

        return new ArrayList<>();
    }

    // Extracts entries from an object node and formats them properly
    private String extractEntries(JsonNode entriesNode) {
        StringBuilder entriesText = new StringBuilder();

        if (entriesNode != null && entriesNode.isArray()) {
            for (JsonNode entry : entriesNode) {
                if (entry.isTextual()) {
                    entriesText.append(entry.asText()).append("\n");
                } else if (entry.isObject() && entry.has("entry")) {
                    entriesText.append(entry.get("entry").asText()).append("\n");
                }
            }
        }

        return entriesText.toString().trim();
    }
}
