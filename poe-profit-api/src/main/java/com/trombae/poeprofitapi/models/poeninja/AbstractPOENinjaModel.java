package com.trombae.poeprofitapi.models.poeninja;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractPOENinjaModel {
    protected String name;
    protected Integer id;
    protected String detailsId;
    protected double chaosValue;
    protected String type;
}
