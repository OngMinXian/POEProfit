package com.trombae.poeprofitapi.models.configs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class Reward {
    private String name;
    private Integer id;
    protected String detailsId;
    private String icon;
    private double probability; // Range: (0, 100]
    private double chaosValue;
    private double expectedValue;
    private boolean unidentified;
    private String poeWatchName;

    public void setProbability(double probability) {
        this.probability = probability;
        recalcExpectedValue();
    }

    public void setChaosValue(double chaosValue) {
        this.chaosValue = chaosValue;
        recalcExpectedValue();
    }

    private void recalcExpectedValue() {
        this.expectedValue = probability / 100.0 * chaosValue;
    }
}
