package com.csms.service;

import com.csms.dto.CreateOverDto;
import com.csms.dto.IdDto;
import com.csms.model.Match;
import com.csms.model.Overs;
import com.csms.model.Users;
import com.csms.repository.MatchRepository;
import com.csms.repository.OverRepository;
import com.csms.repository.UserRepository;
import com.csms.utils.enums.ChooseTo;
import com.csms.utils.enums.OverStatus;
import com.csms.utils.exception.customExceptions.CustomConflictException;
import com.csms.utils.exception.customExceptions.NotFoundException;
import com.csms.utils.messages.ErrorMessages;
import com.csms.utils.messages.SuccessMessages;
import com.csms.utils.respnse.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class OverService {

    private final OverRepository overRepository;
    private final UserRepository userRepository;
    private final MatchRepository matchRepository;

    public ResponseEntity createOver(CreateOverDto overData){
        Match match = matchRepository.findById(overData.getMatchId()).orElseThrow(()->new NotFoundException(ErrorMessages.MATCH_NOT_FOUND));

        List<Overs> currentOvers = match.getOvers().stream().filter((over)->over.getStatus()== OverStatus.CURRENT).collect(Collectors.toList());
        if(currentOvers.size() > 0) throw new CustomConflictException(ErrorMessages.ALREADY_CURRENT_OVER);

        Users bowler = userRepository.findById(overData.getBowlerId()).orElseThrow(()->new NotFoundException(ErrorMessages.USER_NOT_FOUND));

        if(isValidOver(match, bowler)){
            Overs over = new Overs();
            over.setBowler(bowler);
            over.setMatch(match);
            over.setOverNo(match.getOvers().size()+1);
            overRepository.save(over);

            if(match.getOvers().size()+1 == match.getTotalOvers() ){
                match.setInningsBreak(true);
                matchRepository.save(match);
            }

            return SuccessResponse.success(SuccessMessages.OVER_CREATED,over);
        }else{
            throw new CustomConflictException(ErrorMessages.INVALID_OVER);
        }
    }

    public ResponseEntity updateOver(long overId, IdDto overData){
        Overs over = overRepository.findById(overId).orElseThrow(()->new NotFoundException(ErrorMessages.OVER_NOT_FOUND));
        Users bowler = userRepository.findById(overData.getId()).orElseThrow(()->new NotFoundException(ErrorMessages.USER_NOT_FOUND));
        if(bowler.getTeam().getId() == over.getBowler().getTeam().getId() && over.getStatus() == OverStatus.CURRENT){
            over.setBowler(bowler);
            overRepository.save(over);
            return SuccessResponse.success(SuccessMessages.OVER_CREATED,over);
        }else{
            throw new CustomConflictException(ErrorMessages.INVALID_OVER);
        }
    }

    private boolean isValidOver(Match match , Users bowler ){
        ChooseTo action = match.getOptTo();
        boolean trueCase = bowler.getTeam().getId() == match.getToss().getId();

        if(!match.isInningsBreak() && action == ChooseTo.BOWLING && trueCase){
            return true;
        }else if(match.isInningsBreak() && action == ChooseTo.BOWLING && !trueCase){
            return true;
        }else if(!match.isInningsBreak() && action == ChooseTo.BATTING && !trueCase){
            return true;
        }else if(match.isInningsBreak() && action == ChooseTo.BATTING && trueCase){
            return true;
        }else {
            return false;
        }
    }

    public ResponseEntity getOverDetails(long overId){
        Overs over = overRepository.findById(overId).orElseThrow(()->new NotFoundException(ErrorMessages.OVER_NOT_FOUND));
        return SuccessResponse.success(SuccessMessages.OVER_VIEW,over);
    }
}
