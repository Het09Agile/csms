package com.csms.controller;

import com.csms.config.UserDetailsImpl;
import com.csms.dto.AddTeamPlayers;
import com.csms.dto.CreateTeamDto;
import com.csms.dto.UpdateTeamDto;
import com.csms.model.Users;
import com.csms.service.TeamService;
import com.csms.service.UserService;
import com.csms.utils.enums.Role;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
@AllArgsConstructor
@SecurityRequirement(name = "bearer")
public class TeamController {

    private final TeamService teamService;
    private final UserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TM')")
    @PostMapping("/create")
    ResponseEntity createTeam(@Validated @RequestBody CreateTeamDto teamData, @AuthenticationPrincipal UserDetailsImpl principal){
        Users manager = userService.getUserDetails(principal);
        if(manager.getRole() == Role.ADMIN){
            manager = userService.getUserByRoleAndId(teamData.getManager(),Role.TM);
        }
        return teamService.createTeam(teamData, manager);
    }

    @GetMapping("/get")
    ResponseEntity getTeamDetails(@AuthenticationPrincipal UserDetailsImpl principal){
        Users user = userService.getUserDetails(principal);
        return teamService.getTeamDetails(user);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'TM')")
    @PostMapping("/modifyTeamPlayers")
    ResponseEntity addTeamPlayers(@Validated @RequestBody AddTeamPlayers addTeamPlayers,@AuthenticationPrincipal UserDetailsImpl principal){
        Users user = userService.getUserDetails(principal);

        return teamService.modifyTeamPlayers(addTeamPlayers,user);
    }

    @GetMapping("/view/{teamId}")
    ResponseEntity viewTeamDetails(@PathVariable("teamId") long teamId){
        return teamService.getTeamById(teamId);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TM')")
    ResponseEntity updateTeamDetails(@Validated @RequestBody UpdateTeamDto updateTeamData, @AuthenticationPrincipal UserDetailsImpl principal){
        Users user = userService.getUserDetails(principal);
        return teamService.updateTeamDetails(user,updateTeamData);
    }



}
