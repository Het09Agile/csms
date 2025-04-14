package com.csms.service;

import com.csms.model.Ball;
import com.csms.model.Match;
import com.csms.model.Overs;
import com.csms.model.Users;
import com.csms.repository.BallRepository;
import com.csms.repository.MatchRepository;
import com.csms.repository.OverRepository;
import com.csms.repository.UserRepository;
import com.csms.utils.exception.customExceptions.NotFoundException;
import com.csms.utils.messages.ErrorMessages;
import com.csms.utils.messages.SuccessMessages;
import com.csms.utils.respnse.SuccessResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@AllArgsConstructor
public class ScoreBoardService {

    private final OverRepository overRepository;
    private final MatchRepository matchRepository;
    private final BallRepository ballRepository;
    private final UserRepository userRepository;

    public ResponseEntity getMatchScore(long matchId){
        Match match = matchRepository.findById(matchId).orElseThrow(()->new NotFoundException(ErrorMessages.MATCH_NOT_FOUND));
        System.out.println(match.getOvers().size());
        return SuccessResponse.messageOnly(SuccessMessages.SUCCESS);
    }

    public ResponseEntity getPlayerPerformance(long playerId){
        Users player = userRepository.findById(playerId).orElseThrow(()-> new NotFoundException(ErrorMessages.USER_NOT_FOUND));

//        List<Overs> overs = overRepository.findAllByBowler(player);
//        System.out.println(overs.size());
//        List<Ball> ballsPlayed = ballRepository.findAllByBatsman(player);
//        System.out.println(ballsPlayed.size());

//        AtomicInteger totalRuns = new AtomicInteger();
//        ballsPlayed.stream().forEach((ball)->{
//             totalRuns.addAndGet(ball.getRun());
//        });
//        System.out.println(totalRuns);
        return SuccessResponse.messageOnly(SuccessMessages.SUCCESS);
    }
}
