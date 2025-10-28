package com.trombae.pathofbossingapi.controllers;

import com.trombae.pathofbossingapi.models.DTOs.BossDTO;
import com.trombae.pathofbossingapi.services.BossingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bosses")
@CrossOrigin(origins = "http://localhost:4200")
public class BossingController {
    private BossingService bossingService;

    public BossingController(BossingService bossingService) {
        this.bossingService = bossingService;
    }

    @GetMapping
    public List<BossDTO> getAllBossingTable() {
        return bossingService.getAllBossDTOs();
    }

    @GetMapping("/{bossId}")
    public BossDTO getBossingTable(@PathVariable String bossId) {
        return bossingService.getBossDTOById(bossId);
    }
}
