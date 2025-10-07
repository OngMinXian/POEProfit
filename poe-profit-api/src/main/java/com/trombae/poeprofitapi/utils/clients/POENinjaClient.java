package com.trombae.poeprofitapi.utils.clients;

import com.fasterxml.jackson.databind.JsonNode;

import static com.trombae.poeprofitapi.constants.POENinjaConstants.APIEndpoints.*;

public class POENinjaClient {
    private static final String API_URL_FORMAT = "https://poe.ninja/api/data/%s?league=%s&type=%s";

    public static String getLeague() {
        // TODO: Make not hard-coded
        return "Mercenaries";
    }

    private static String getAPIURL(String endpoint, String typeName) {
        return String.format(API_URL_FORMAT, endpoint, getLeague(), typeName);
    }

    public static JsonNode getCurrencyOverview(String typeName) {
        String apiURL = getAPIURL(CURRENCY_ENDPOINT, typeName);
        return HttpGetClient.getAsJson(apiURL);
    }

    public static JsonNode getItemOverview(String typeName) {
        String apiURL = getAPIURL(ITEM_ENDPOINT, typeName);
        return HttpGetClient.getAsJson(apiURL);
    }
}
