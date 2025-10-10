package com.trombae.poeprofitapi.models.configs;

import lombok.Data;

@Data
public class Cost {
    private String name;
    private Integer id;
    protected String detailsId;
    private int quantity;
    private String icon;
    private double chaosValue;
    private double totalCost;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        recalcTotalCost();
    }

    public void setChaosValue(double chaosValue) {
        this.chaosValue = chaosValue;
        recalcTotalCost();
    }

    private void recalcTotalCost() {
        this.totalCost = quantity * chaosValue;
    }
}
