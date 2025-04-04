package com.csms.service;

import com.csms.dto.AddTeamPlayers;
import com.csms.dto.CreateTeamDto;
import com.csms.dto.UpdateTeamDto;
import com.csms.model.Teams;
import com.csms.model.Users;
import com.csms.repository.TeamRepository;
import com.csms.repository.UserRepository;
import com.csms.utils.enums.Role;
import com.csms.utils.exception.customExceptions.NotFoundException;
import com.csms.utils.messages.ErrorMessages;
import com.csms.utils.messages.SuccessMessages;
import com.csms.utils.respnse.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    private Teams getTeamForAdminOrManager(long teamId,Users user){
        Teams team;
        if(user.getRole() == Role.ADMIN){
            team = teamRepository.findById(teamId).orElseThrow(()->new NotFoundException(ErrorMessages.TEAM_NOT_FOUND));
        }
        else{
//       API is Accessible to only 2 role ADMIN and TM so we can take this if condition as else
            team = teamRepository.findByManager(user).orElseThrow(()->new NotFoundException(ErrorMessages.TEAM_NOT_FOUND));
        }
        return team;
    }

    public ResponseEntity createTeam(CreateTeamDto teamData, Users manager){
        Teams team = new Teams();
        team.setName(teamData.getName());
        team.setCountry(teamData.getCountry());
        team.setManager(manager);
        teamRepository.save(team);
        return SuccessResponse.success(SuccessMessages.TEAM_CREATED,team);

    }

    public ResponseEntity getTeamDetails(Users user){
        Teams team = user.getTeam();
        if(user.getRole() == Role.TM ){
            team = teamRepository.findByManager(user).orElseThrow(()->new NotFoundException(ErrorMessages.TEAM_NOT_FOUND));
        }
        return SuccessResponse.success(SuccessMessages.TEAM_GET,team);
    }

    public ResponseEntity modifyTeamPlayers(AddTeamPlayers players, Users user){
        Teams team = this.getTeamForAdminOrManager(players.getTeamId(), user);

//         adding players to team
        players.getAddPlayers().stream().forEach((playerId)->{
            Users player = userRepository.findById(playerId).orElseThrow(()->new NotFoundException(ErrorMessages.USER_NOT_FOUND));
            player.setTeam(team);
            userRepository.save(player);
        });

//        removing players from team
        players.getRemovePlayers().stream().forEach((playerId)->{
            Users player = userRepository.findByIdAndTeam(playerId, team).orElseThrow(()-> new NotFoundException(ErrorMessages.USER_NOT_FOUND));
            player.setTeam(null);
            userRepository.save(player);
        });
        return SuccessResponse.messageOnly(SuccessMessages.PLAYERS_MODIFIES_TO_TEAM);
    }

    public ResponseEntity getTeamById(long id){
        Teams teams = teamRepository.findById(id).orElseThrow(()->new NotFoundException(ErrorMessages.TEAM_NOT_FOUND));
        return SuccessResponse.dataOnly(teams);
    }

    public ResponseEntity updateTeamDetails(Users user, UpdateTeamDto teamData){
        Teams team = this.getTeamForAdminOrManager(teamData.getTeamId(), user);

        team.setName(teamData.getName());
        team.setCountry(teamData.getCountry());
        if(user.getRole()==Role.ADMIN){
            if(teamData.getManager() != 0){
                Users manager = userRepository.findByIdAndRole(teamData.getManager(),Role.TM).orElseThrow(()->new NotFoundException(ErrorMessages.USER_NOT_FOUND));
                team.setManager(manager);
            }
        }
        teamRepository.save(team);
        return SuccessResponse.messageOnly(SuccessMessages.TEAM_UPDATED);
    }

}
