package com.trombae.poeprofitapi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trombae.poeprofitapi.models.DTOs.BossDTO;
import com.trombae.poeprofitapi.models.configs.BossingConfig;
import com.trombae.poeprofitapi.repositories.POENinjaRepository;
import com.trombae.poeprofitapi.repositories.POEWatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BossingService {
    @Autowired
    private POENinjaRepository poeNinjaRepository;
    @Autowired
    private POEWatchRepository poeWatchRepository;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String BOSSING_CONFIG_DIR = "/opt/trombae/bossing/";

    public BossingService(POENinjaRepository poeNinjaRepository, POEWatchRepository poeWatchRepository) {
        this.poeNinjaRepository = poeNinjaRepository;
        this.poeWatchRepository = poeWatchRepository;
    }

    private BossingConfig getBossingConfig(File bossConfigFile) {
        try {
            BossingConfig bossingConfig = objectMapper.readValue(bossConfigFile, BossingConfig.class);
            bossingConfig.updateCosts(poeNinjaRepository);
            bossingConfig.updateRewards(poeNinjaRepository, poeWatchRepository);
            return bossingConfig;
        } catch (Exception ex) {
            log.error("Unable to read config file '{}'. Exception: {}", bossConfigFile.getName(), ex.getMessage());
            return null;
        }
    }

    private List<BossingConfig> getAllBossingConfigs() {
        File bossingConfigDir = new File(BOSSING_CONFIG_DIR);
        File[] bossingConfigFileNames = bossingConfigDir.listFiles((dirName, fileName) -> fileName.toLowerCase().endsWith(".json"));
        return Arrays.stream(bossingConfigFileNames).map(this::getBossingConfig).filter(Objects::nonNull).collect(Collectors.toList());
    }

    public List<BossDTO> getAllBossDTOs() {
        List<BossDTO> allBossDTOs = new ArrayList<>();

        List<BossingConfig> allBossingConfigs = getAllBossingConfigs();
        for (BossingConfig config : allBossingConfigs) {
            allBossDTOs.add(new BossDTO(
                    config.getName(),
                    config.getId(),
                    config.getTotalCostInChaos(),
                    config.getTotalExpectedValueInChaos(),
                    config.getProfit(),
                    config.getCosts(),
                    config.getRewards(),
                    config.getWikiUrl(),
                    config.getImageUrl(),
                    config.getIcon()
            ));
        }

        return allBossDTOs;
    }

    public BossDTO getBossDTOById(String id) {
        return getAllBossDTOs().stream().filter(boss -> boss.getId().equals(id)).findFirst().get();
    }
}
