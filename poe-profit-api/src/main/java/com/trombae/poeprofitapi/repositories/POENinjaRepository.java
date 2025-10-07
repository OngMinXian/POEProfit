package com.trombae.poeprofitapi.repositories;

import com.trombae.poeprofitapi.models.poeninja.AbstractPOENinjaModel;
import com.trombae.poeprofitapi.utils.parsers.POENinjaCurrencyParser;
import com.trombae.poeprofitapi.utils.parsers.POENinjaItemParser;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class POENinjaRepository {
    private HashMap<String, String> nameToDetailsIDCache;
    private HashMap<String, AbstractPOENinjaModel> detailsIDToItemCache;

    public POENinjaRepository() {
        this.nameToDetailsIDCache = new HashMap<>();
        this.detailsIDToItemCache = new HashMap<>();
        refreshCache();
    }

    public double getChaosValueOfItem(String name, String detailsID) {
        // TODO: Handle unidentified items
        if (detailsID == null) {
            detailsID = getNameToDetailsIDCache().get(name);
        }
        return detailsIDToItemCache.get(detailsID).getChaosValue();
    }

    private HashMap<String, String> getNameToDetailsIDCache() {
        // TODO: Refresh prices every 5 minutes
        return this.nameToDetailsIDCache;
    }

    private HashMap<String, AbstractPOENinjaModel> getDetailsIDToItemCache() {
        // TODO: Refresh prices every 5 minutes
        return this.detailsIDToItemCache;
    }

    private void refreshCache() {
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
