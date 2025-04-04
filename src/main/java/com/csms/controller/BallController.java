package com.csms.controller;

import com.csms.dto.CreateBallDto;
import com.csms.service.BallService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ball")
@AllArgsConstructor
public class BallController {

    private final BallService ballService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TM')")
    @PostMapping("/create")
    public ResponseEntity createBall(@RequestBody CreateBallDto body){
        return ballService.createBall(body);
    }

    @GetMapping("/view/{ballId}")
    public ResponseEntity viewBall(@PathVariable("ballId") long ballId){
        return ballService.viewBall(ballId);
    }
}
