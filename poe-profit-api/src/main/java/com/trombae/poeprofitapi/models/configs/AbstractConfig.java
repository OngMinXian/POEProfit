package com.trombae.poeprofitapi.models.configs;

import com.trombae.poeprofitapi.repositories.POENinjaRepository;
import com.trombae.poeprofitapi.repositories.POEWatchRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

@Slf4j
@Data
public class AbstractConfig {
    private String name;
    private String id;
    private ArrayList<Cost> costs;
    private ArrayList<Reward> rewards;

    private final String UNABLE_TO_GET_CHAOS_VALUE_LOG = "Unable to get chaos value of {}";

    public void updateCosts(POENinjaRepository poeNinjaRepository) {
        for (Cost cost : costs) {
            try {
                cost.setChaosValue(poeNinjaRepository.getChaosValueOfItem(cost.getName(), cost.getDetailsId()));
                cost.setIcon(poeNinjaRepository.getIconOfItem(cost.getName(), cost.getDetailsId()));
            } catch (Exception ex) {
                log.error(UNABLE_TO_GET_CHAOS_VALUE_LOG, cost.getName(), ex);
                cost.setChaosValue(0);
            }
        }
    }

    public void updateRewards(POENinjaRepository poeNinjaRepository, POEWatchRepository poeWatchRepository) {
        for (Reward reward : rewards) {
            try {
                reward.setChaosValue(reward.isUnidentified() ?
                        poeWatchRepository.getChaosValueOfItem(reward.getPoeWatchName()) :
                        poeNinjaRepository.getChaosValueOfItem(reward.getName(), reward.getDetailsId()));
                reward.setIcon(poeNinjaRepository.getIconOfItem(reward.getName(), reward.getDetailsId()));
            } catch (Exception ex) {
                log.error(UNABLE_TO_GET_CHAOS_VALUE_LOG, reward.getName(), ex);
                reward.setChaosValue(0);
            }
        }
    }

    public double getTotalCostInChaos() {
        return this.getCosts().stream().map(Cost::getTotalCost).reduce(Double::sum).orElseGet(() -> 0.0);
    }

    public double getTotalExpectedValueInChaos() {
        return this.getRewards().stream().map(Reward::getExpectedValue).reduce(Double::sum).orElseGet(() -> 0.0);
    }

    public double getProfit() {
        return getTotalExpectedValueInChaos() - getTotalCostInChaos();
    }
}
