package com.csms.service;

import com.csms.dto.*;
import com.csms.model.Match;
import com.csms.model.Teams;
import com.csms.model.Users;
import com.csms.repository.MatchRepository;
import com.csms.repository.TeamRepository;
import com.csms.utils.enums.MatchStatus;
import com.csms.utils.enums.Role;
import com.csms.utils.enums.SortOrder;
import com.csms.utils.exception.customExceptions.CustomConflictException;
import com.csms.utils.exception.customExceptions.NotFoundException;
import com.csms.utils.messages.ErrorMessages;
import com.csms.utils.messages.SuccessMessages;
import com.csms.utils.respnse.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.csms.utils.messages.ErrorMessages.MATCH_NOT_FOUND;

@Service
@AllArgsConstructor
public class MatchService {


    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public ResponseEntity createMatch(CreateMatchDto matchData){
        Match match = new Match();
        match.setDate(matchData.getDate());
        match.setVenue(matchData.getVenue());
        match.setTotalOvers(matchData.getTotalOvers());
        List<Teams> teams = teamRepository.findAllById(new ArrayList<>(matchData.getTeams()));
        match.setTeams(teams.stream().collect(Collectors.toSet()));
        matchRepository.save(match);
        return SuccessResponse.success(SuccessMessages.MATCH_CREATED,match);
    }

    public ResponseEntity updateMatch(UpdateMatchDto matchData,long matchId){
        Match match = matchRepository.findById(matchId).orElseThrow(()-> new NotFoundException(MATCH_NOT_FOUND));
        if(matchData.getDate()!=null)  match.setDate(matchData.getDate()) ;
        if(matchData.getVenue()!=null) match.setVenue(matchData.getVenue());
        if(matchData.getTotalOvers() > 0 ) match.setTotalOvers(matchData.getTotalOvers());
        List<Teams> teams = teamRepository.findAllById(new ArrayList<>(matchData.getTeams()));
        if(teams.size() == 2) match.setTeams(teams.stream().collect(Collectors.toSet()));
//      If match is 'ACTIVE' then you can change the toss result.
        if(match.getStatus() == MatchStatus.ACTIVE){
            List<Teams> tossWonTeam = teams.stream().filter((team)-> team.getId() == matchData.getTeamId()).collect(Collectors.toList());
            if(tossWonTeam.get(0) != null) match.setToss(tossWonTeam.get(0));
            if(matchData.getChooseTo() != null) match.setOptTo(matchData.getChooseTo());
        }
        matchRepository.save(match);
        return SuccessResponse.success(SuccessMessages.MATCH_UPDATED,match);
    }

    public ResponseEntity tossUpdate(TossUpdateDto matchData) {
        Match match = matchRepository.findById(matchData.getMatchId()).orElseThrow(()-> new NotFoundException(MATCH_NOT_FOUND));
        Teams teams = teamRepository.findById(matchData.getTeamId()).orElseThrow(()-> new NotFoundException(ErrorMessages.TEAM_NOT_FOUND));
        if(match.getDate().getTime() > new Date().getTime() + 1000 * 60 * 60){
            throw new CustomConflictException(ErrorMessages.MATCH_START_EARLY);
        }
        match.setToss(teams);
        match.setOptTo(matchData.getChooseTo());
        match.setStatus(MatchStatus.ACTIVE);
        matchRepository.save(match);
        return SuccessResponse.success(SuccessMessages.MATCH_STARTED,match);
    }

    public ResponseEntity viewMatch(long matchId) {
        Match match = matchRepository.findById(matchId).orElseThrow(()->new NotFoundException(ErrorMessages.MATCH_NOT_FOUND));
        return SuccessResponse.success(SuccessMessages.MATCH_FETCHED, match);
    }

    public ResponseEntity listMatch(PaginationDto body) {
        Sort.Direction direction = body.getSortOrder() == SortOrder.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest page = PageRequest.of(body.getPage(),body.getLimit(), Sort.by(direction, body.getSortBy()));
        Page<Match> records= matchRepository.findAll(page);
        return SuccessResponse.success(SuccessMessages.MATCH_FETCHED,records);
    }

    public ResponseEntity myMatchList(PaginationDto body, Users users) {
        Teams team = users.getTeam();
        if(users.getRole() == Role.TM){
            System.out.println("TM condition");
            team = teamRepository.findByManager(users).orElseThrow(()->new NotFoundException(ErrorMessages.TEAM_NOT_FOUND));
        }


        Sort.Direction direction = body.getSortOrder() == SortOrder.ASC ? Sort.Direction.ASC : Sort.Direction.DESC;
        PageRequest page = PageRequest.of(body.getPage(),body.getLimit(), Sort.by(direction, body.getSortBy()));

        Page<Match> records= matchRepository.findAllByTeams(team,page);
        return SuccessResponse.success(SuccessMessages.MATCH_FETCHED,records);
    }
}