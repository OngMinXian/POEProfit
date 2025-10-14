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

@Slf4j
@Component
public class POEWatchRepository {
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    private HashMap<String, Double> itemNameToChaosValueCache = new HashMap<>();

    public double getChaosValueOfItem(String name) {
        if (itemNameToChaosValueCache.containsKey(name)) {
            return itemNameToChaosValueCache.get(name);
        }

        JsonNode itemNode = POEWatchClient.getItem(name).get(0);
        try {
            POEWatchItem item = objectMapper.readValue(itemNode.toString(), POEWatchItem.class);
            itemNameToChaosValueCache.put(name, item.getMean());
            return item.getMean();
        } catch (Exception ex) {
            log.error("Unable to parse item Json data: {}", itemNode, ex);
            return 0;
        }
    }

    // TODO: Clean up name + level work around
    public double getChaosValueOfItem(String name, Integer level) {
        if (level == null) {
            return getChaosValueOfItem(name);
        }

        String nameAndLevel = name + level;

        if (itemNameToChaosValueCache.containsKey(nameAndLevel)) {
            return itemNameToChaosValueCache.get(nameAndLevel);
        }

        JsonNode itemArray = POEWatchClient.getItem(name);
        try {
            POEWatchItem item = null;
            for (JsonNode itemNode : itemArray) {
                item = objectMapper.readValue(itemNode.toString(), POEWatchItem.class);
                if (item.getItemLevel() == level) {
                    itemNameToChaosValueCache.put(nameAndLevel, item.getMean());
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
        // TODO: Do not clear prices of items that cannot be retrieved
        log.info("Refreshing POE Watch Repository Cache");
        itemNameToChaosValueCache = new HashMap<>();
    }
}
