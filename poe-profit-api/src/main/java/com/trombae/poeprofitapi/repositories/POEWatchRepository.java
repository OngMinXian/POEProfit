package com.trombae.poeprofitapi.repositories;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trombae.poeprofitapi.constants.RepositoryConstants;
import com.trombae.poeprofitapi.poewatch.POEWatchItem;
import com.trombae.poeprofitapi.utils.clients.POEWatchClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class POEWatchRepository {
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    private HashMap<String, HashMap<Integer, Double>> itemNameToLevelToChaosValueCache = new HashMap<>();
    private static final Integer ITEM_NO_LEVEL_VALUE = -1;

    public double getChaosValueOfItem(String name) {
        if (!itemNameToLevelToChaosValueCache.containsKey(name)) {
            itemNameToLevelToChaosValueCache.put(name, new HashMap<>());
        }
        if (!itemNameToLevelToChaosValueCache.get(name).containsKey(ITEM_NO_LEVEL_VALUE)) {
            double chaosPrice = getAndParseChaosValueOfItemFromClient(name);
            itemNameToLevelToChaosValueCache.get(name).put(ITEM_NO_LEVEL_VALUE, chaosPrice);
        }
        return itemNameToLevelToChaosValueCache.get(name).get(ITEM_NO_LEVEL_VALUE);
    }

    private double getAndParseChaosValueOfItemFromClient(String name) {
        JsonNode itemNode = null;
        try {
             itemNode = POEWatchClient.getItem(name).get(0);
            POEWatchItem item = objectMapper.readValue(itemNode.toString(), POEWatchItem.class);
            return item.getMean();
        } catch (Exception ex) {
            log.error("Unable to parse item Json data: {}", itemNode, ex);
            return 0;
        }
    }

    public double getChaosValueOfItem(String name, Integer level) {
        if (level == null) {
            return getChaosValueOfItem(name);
        }
        if (!itemNameToLevelToChaosValueCache.containsKey(name)) {
            itemNameToLevelToChaosValueCache.put(name, new HashMap<>());
        }
        if (!itemNameToLevelToChaosValueCache.get(name).containsKey(level)) {
            double chaosPrice = getAndParseChaosValueOfItemFromClient(name, level);
            itemNameToLevelToChaosValueCache.get(name).put(level, chaosPrice);
        }
        return itemNameToLevelToChaosValueCache.get(name).get(level);
    }

    private double getAndParseChaosValueOfItemFromClient(String name, Integer level) {
        JsonNode itemArray = null;
        try {
            itemArray = POEWatchClient.getItem(name);
            POEWatchItem item = null;
            for (JsonNode itemNode : itemArray) {
                item = objectMapper.readValue(itemNode.toString(), POEWatchItem.class);
                if (item.getItemLevel() == level) {
                    if (!itemNameToLevelToChaosValueCache.containsKey(name)) {
                        itemNameToLevelToChaosValueCache.put(name, new HashMap<>());
                    }
                    itemNameToLevelToChaosValueCache.get(name).put(level, item.getMean());
                    break;
                }
            }
            return item.getMean();
        } catch (Exception ex) {
            log.error("Unable to parse item Json data: {}", itemArray, ex);
            return 0;
        }
    }

    @Scheduled(fixedRate = RepositoryConstants.CACHE_DURATION, initialDelay = RepositoryConstants.CACHE_DURATION)
    private void refreshCache() {
        log.info("Refreshing POE Watch Repository Cache");
        for (Map.Entry<String, HashMap<Integer, Double>> nameToLevel : itemNameToLevelToChaosValueCache.entrySet()) {
            String name = nameToLevel.getKey();
            for (Map.Entry<Integer, Double> levelToChaosValue : nameToLevel.getValue().entrySet()) {
                Integer level = levelToChaosValue.getKey();
                double newChaosValue = level == ITEM_NO_LEVEL_VALUE ?
                        getAndParseChaosValueOfItemFromClient(name) :
                        getAndParseChaosValueOfItemFromClient(name, level);
                if (newChaosValue != 0) {
                    itemNameToLevelToChaosValueCache.get(name).put(level, newChaosValue);
                }
            }
        }
    }
}
