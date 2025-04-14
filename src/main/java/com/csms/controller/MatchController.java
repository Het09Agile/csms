package com.csms.controller;

import com.csms.config.UserDetailsImpl;
import com.csms.dto.CreateMatchDto;
import com.csms.dto.PaginationDto;
import com.csms.dto.TossUpdateDto;
import com.csms.dto.UpdateMatchDto;
import com.csms.model.Users;
import com.csms.service.MatchService;
import com.csms.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/match")
@AllArgsConstructor
@SecurityRequirement(name = "bearer")
public class MatchController {

    private final MatchService matchService;
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TM')")
    @PostMapping("/create")
    ResponseEntity createMatch(@Validated @RequestBody CreateMatchDto matchData){
        return matchService.createMatch(matchData);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TM')")
    @PutMapping("/update/{matchId}")
    ResponseEntity updateMatch(@Validated @RequestBody UpdateMatchDto matchData, @PathVariable("matchId") long matchId){
        return matchService.updateMatch(matchData,matchId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TM')")
    @PutMapping("/tossUpdate")
    ResponseEntity tossUpdate(@Validated @RequestBody TossUpdateDto tossUpdateDto){
        return matchService.tossUpdate(tossUpdateDto);
    }
//    view match
    @GetMapping("/view/{matchId}")
    ResponseEntity viewMatch(@PathVariable("matchId") long matchId){
        return matchService.viewMatch(matchId);
    }
//    list match
    @PostMapping("/list")
    ResponseEntity listMatch(@Validated @RequestBody PaginationDto body){
        return matchService.listMatch(body);
    }
//    myMatches
    @PreAuthorize("!hasAuthority('ADMIN')")
    @PostMapping("/myMatchList")
    ResponseEntity myMatchList(@Validated @RequestBody PaginationDto body, @AuthenticationPrincipal UserDetailsImpl principal){
        Users user = userService.getUserDetails(principal);
        return matchService.myMatchList(body, user);
    }
//    innings break
}
