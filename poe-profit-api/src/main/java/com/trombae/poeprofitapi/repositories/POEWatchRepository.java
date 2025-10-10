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
            itemNameToChaosValueCache.put(item.getName(), item.getMean());
            return item.getMean();
        } catch (Exception ex) {
            log.error("Unable to parse item Json data: {}. Exception: {}", itemNode, ex.getMessage());
            return 0;
        }
    }

    @Scheduled(fixedRate = RepositoryConstants.CACHE_DURATION, initialDelay = RepositoryConstants.CACHE_DURATION)
    private void refreshCache() {
        log.info("Refreshing POE Watch Repository Cache");
        itemNameToChaosValueCache = new HashMap<>();
    }
}
