package com.trombae.poeprofitapi.models.DTOs;

import com.trombae.poeprofitapi.models.configs.Cost;
import com.trombae.poeprofitapi.models.configs.Reward;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BossDTO {
    public String name;
    public String id;
    public double costInChaos;
    public double expectedValueInChaos;
    public double profitInChaos;
    public List<Cost> costs;
    public List<Reward> rewards;
}
