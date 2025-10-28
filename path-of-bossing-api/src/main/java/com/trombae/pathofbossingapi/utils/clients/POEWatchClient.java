package com.trombae.pathofbossingapi.utils.clients;

import com.fasterxml.jackson.databind.JsonNode;

public class POEWatchClient {
    private static final String API_URL_NAME_QUERY_FORMAT = "https://api.poe.watch/search?league=%s&q=%s";

    public static String getLeague() {
        // TODO: Make not hard-coded
        return "Mercenaries";
    }

    public static JsonNode getItem(String itemName) {
        String itemAPIURL = String.format(API_URL_NAME_QUERY_FORMAT, getLeague(), itemName);
        return HttpGetClient.getAsJson(itemAPIURL);
    }
}
