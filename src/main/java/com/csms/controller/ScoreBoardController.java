package com.csms.controller;

import com.csms.service.ScoreBoardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/score")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer")
public class ScoreBoardController {

    private final ScoreBoardService scoreBoardService;

    @GetMapping("/match/{matchId}")
    public ResponseEntity getMatchScore(@PathVariable("matchId") long matchId){
        return scoreBoardService.getMatchScore(matchId);
    }

    @GetMapping("/player/performance/{playerId}")
    public ResponseEntity getPlayerPerformance(@PathVariable("playerId") long playerId){
        return scoreBoardService.getPlayerPerformance(playerId);
    }
}
