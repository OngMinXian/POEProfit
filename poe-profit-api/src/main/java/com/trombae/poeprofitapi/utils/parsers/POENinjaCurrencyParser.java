package com.trombae.poeprofitapi.utils.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.trombae.poeprofitapi.models.poeninja.AbstractPOENinjaModel;
import com.trombae.poeprofitapi.models.poeninja.Currency;
import com.trombae.poeprofitapi.utils.clients.POENinjaClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Objects;

import static com.trombae.poeprofitapi.constants.POENinjaConstants.*;
import static com.trombae.poeprofitapi.constants.POENinjaConstants.APIEndpoints.LINES_JSON_FIELD;

@Slf4j
public class POENinjaCurrencyParser extends AbstractPOENinjaParser {
    public static ArrayList<AbstractPOENinjaModel> getAllCurrencies() {
        ArrayList<AbstractPOENinjaModel> currencies = new ArrayList<>();
        for (CurrencyTypes currencyType : CurrencyTypes.values()) {
            String typeName = currencyType.getTypeName();
            JsonNode currencyOverview = POENinjaClient.getCurrencyOverview(typeName);
            JsonNode currencyArray = currencyOverview.get(LINES_JSON_FIELD);
            currencies.addAll(parseCurrencyData(currencyArray, typeName));
        }
        return currencies;
    }

    private static ArrayList<AbstractPOENinjaModel> parseCurrencyData(JsonNode currencyArray, String typeName) {
        ArrayList<AbstractPOENinjaModel> currencies = new ArrayList<>();
        for (JsonNode currencyNode : currencyArray) {
            try {
                Currency currency = objectMapper.readValue(currencyNode.toString(), Currency.class);
                if (currency.getId() != null) {
                    currency.setType(typeName);
                    currencies.add(currency);
                }
            } catch (JsonProcessingException ex) {
                log.error("Unable to parse currency Json data: {}. Exception: {}", currencyNode, ex.getMessage());
            }
        }
        return currencies;
    }
}
