package com.trombae.poeprofitapi.repositories;

import com.trombae.poeprofitapi.constants.RepositoryConstants;
import com.trombae.poeprofitapi.models.poeninja.AbstractPOENinjaModel;
import com.trombae.poeprofitapi.utils.parsers.POENinjaCurrencyParser;
import com.trombae.poeprofitapi.utils.parsers.POENinjaItemParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Slf4j
@Component
public class POENinjaRepository {
    private HashMap<String, String> nameToDetailsIDCache;
    private HashMap<String, AbstractPOENinjaModel> detailsIDToItemCache;

    public POENinjaRepository() {
        this.nameToDetailsIDCache = new HashMap<>();
        this.detailsIDToItemCache = new HashMap<>();
        refreshCache();
    }

    public double getChaosValueOfItem(String name, String detailsID) {
        if (detailsID == null) {
            detailsID = getNameToDetailsIDCache().get(name);
        }
        return detailsIDToItemCache.get(detailsID).getChaosValue();
    }

    public String getIconOfItem(String name, String detailsID) {
        if (detailsID == null) {
            detailsID = getNameToDetailsIDCache().get(name);
        }
        String iconUrl = detailsIDToItemCache.get(detailsID).getIcon();
        if (iconUrl == null) {
            iconUrl = RepositoryConstants.ICON_ERROR_URL;
        }
        return iconUrl;
    }

    private HashMap<String, String> getNameToDetailsIDCache() {
        return this.nameToDetailsIDCache;
    }

    private HashMap<String, AbstractPOENinjaModel> getDetailsIDToItemCache() {
        return this.detailsIDToItemCache;
    }

    @Scheduled(fixedRate = RepositoryConstants.CACHE_DURATION, initialDelay = RepositoryConstants.CACHE_DURATION)
    private void refreshCache() {
        log.info("Refreshing POE Ninja Repository Cache");
        for (AbstractPOENinjaModel currency : POENinjaCurrencyParser.getAllCurrencies()) {
            getNameToDetailsIDCache().put(currency.getName(), currency.getDetailsId());
            getDetailsIDToItemCache().put(currency.getDetailsId(), currency);
        }
        for (AbstractPOENinjaModel item : POENinjaItemParser.getAllItems()) {
            getNameToDetailsIDCache().put(item.getName(), item.getDetailsId());
            getDetailsIDToItemCache().put(item.getDetailsId(), item);
        }
    }
}
