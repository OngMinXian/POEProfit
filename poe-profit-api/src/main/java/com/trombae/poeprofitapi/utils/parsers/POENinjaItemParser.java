package com.trombae.poeprofitapi.utils.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.trombae.poeprofitapi.constants.POENinjaConstants;
import com.trombae.poeprofitapi.models.poeninja.AbstractPOENinjaModel;
import com.trombae.poeprofitapi.models.poeninja.Item;
import com.trombae.poeprofitapi.utils.clients.POENinjaClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

import static com.trombae.poeprofitapi.constants.POENinjaConstants.APIEndpoints.LINES_JSON_FIELD;

@Slf4j
public class POENinjaItemParser extends AbstractPOENinjaParser {
    public static ArrayList<AbstractPOENinjaModel> getAllItems() {
        ArrayList<AbstractPOENinjaModel> items = new ArrayList<>();
        for (POENinjaConstants.ItemTypes ItemType : POENinjaConstants.ItemTypes.values()) {
            String typeName = ItemType.getTypeName();
            JsonNode itemOverview = POENinjaClient.getItemOverview(typeName);
            JsonNode itemArray = itemOverview.get(LINES_JSON_FIELD);
            items.addAll(parseItemData(itemArray, typeName));
        }
        return items;
    }

    private static ArrayList<AbstractPOENinjaModel> parseItemData(JsonNode ItemArray, String typeName) {
        ArrayList<AbstractPOENinjaModel> items = new ArrayList<>();
        for (JsonNode itemNode : ItemArray) {
            try {
                Item item = objectMapper.readValue(itemNode.toString(), Item.class);
                if (item.getId() != null) {
                    item.setType(typeName);
                    items.add(item);
                }
            } catch (JsonProcessingException ex) {
                log.error("Unable to parse Item Json data: {}. Exception: {}", itemNode, ex.getMessage());
            }
        }
        return items;
    }
}
