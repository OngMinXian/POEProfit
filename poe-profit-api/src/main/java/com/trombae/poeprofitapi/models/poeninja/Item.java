package com.trombae.poeprofitapi.models.poeninja;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Item extends AbstractPOENinjaModel {
    private String baseType;
    private int stackSize;
    private String variant;

    // Equipment
    private int links;

    // Skill Gem
    private int gemLevel;
    private int gemQuality;
    private boolean corrupted;

    // Base Type
    private int levelRequired;
}
