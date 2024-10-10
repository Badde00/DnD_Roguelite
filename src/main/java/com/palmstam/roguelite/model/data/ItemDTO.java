package com.palmstam.roguelite.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ItemDTO {
    private String name;

    @JsonDeserialize(using = EntriesDeserializer.class)
    private List<Object> entries; // For descriptions

    @JsonProperty("_fValue")
    private int price;

    private String rarity;
    private String source;
    private int page;

    @JsonProperty("_fIsMundane")
    private boolean isMundane;

    @JsonProperty("_typeHtml")
    private String type;

    // Unneeded fields in the JSON
//    private String reqAttune;
//    private List<String> reqAttuneTags;
//    private boolean wondrous;
//    private int weight;
//    private String bonusSpellAttack;
//    private String bonusSpellSaveDc;
}

