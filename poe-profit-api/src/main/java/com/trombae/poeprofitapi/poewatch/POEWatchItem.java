package com.trombae.poeprofitapi.poewatch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class POEWatchItem {
    private String name;
    private double mean;
    private Integer itemLevel;
}
