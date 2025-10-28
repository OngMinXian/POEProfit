package com.trombae.pathofbossingapi.models.poeninja;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Currency extends AbstractPOENinjaModel {
    private String currencyTypeName;
    private String detailsId;
    private Receive receive;
    private double chaosEquivalent;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Receive {
        private Integer get_currency_id;
    }

    private void setReceive(Receive receive) {
        this.receive = receive;
        if (receive != null && receive.getGet_currency_id() != null) {
            this.id = receive.getGet_currency_id();
        }
    }

    @Override
    public String getName() {
        return this.currencyTypeName;
    }

    @Override
    public double getChaosValue() {
        return this.getChaosEquivalent();
    }
}
