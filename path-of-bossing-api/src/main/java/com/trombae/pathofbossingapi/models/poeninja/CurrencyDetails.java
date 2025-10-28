package com.trombae.pathofbossingapi.models.poeninja;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyDetails {
    private String name;
    private String tradeId;
    private String icon;
}
