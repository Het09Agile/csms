package com.csms.service;

import com.csms.dto.BallCreateResponseDto;
import com.csms.dto.CreateBallDto;
import com.csms.model.Ball;
import com.csms.model.Match;
import com.csms.model.Overs;
import com.csms.model.Users;
import com.csms.repository.BallRepository;
import com.csms.repository.MatchRepository;
import com.csms.repository.OverRepository;
import com.csms.repository.UserRepository;
import com.csms.utils.enums.BallType;
import com.csms.utils.enums.MatchStatus;
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
public class BallService {

    private final BallRepository ballRepository;
    private final UserRepository userRepository;
    private final OverRepository overRepository;
    private final MatchRepository matchRepository;

    public ResponseEntity createBall(CreateBallDto body){
        boolean overCompleted = false ;
        Users batsMan = userRepository.findById(body.getBatsmanId()).orElseThrow(()-> new NotFoundException(ErrorMessages.BATSMAN_NOT_FOUND));
        Overs over  = overRepository.findById(body.getOverId()).orElseThrow(()-> new NotFoundException(ErrorMessages.OVER_NOT_FOUND));

        if(over.getStatus()==OverStatus.COMPLETED){
            throw new CustomConflictException(ErrorMessages.OVER_ALREADY_COMPLETED);
        }

        List<Ball> allFairBallsFromOver = ballRepository.findAllByOver(over)
                .stream().filter(ball->ball.getBallType() == BallType.FAIR || ball.getBallType() == BallType.FREE_HIT ).collect(Collectors.toList());

        if(allFairBallsFromOver.size()==5 && body.getBallType()==BallType.FAIR) {
            over.setStatus(OverStatus.COMPLETED);
            overRepository.save(over);
            if(over.getOverNo() == over.getMatch().getTotalOvers() *2){
                Match match = over.getMatch();
                match.setStatus(MatchStatus.COMPLETED);
                matchRepository.save(match);
            }
            overCompleted = true;
        }

        Ball ball = new Ball();

        ball.setBatsman(batsMan);
        ball.setOver(over);
        ball.setBallOfOver(allFairBallsFromOver.size() + 1);
        ball.setBallType(body.getBallType());
        ball.setRun(body.getRun());
        ball.setWicketType(body.getWicketType());
        ball.setExtraRun(body.getExtraRun());
        ball.setExtraRunType(body.getExtraRunType());
        ball.setCommentary(body.getCommentary());
        ball.setIsFour(body.getIsFour());
        ball.setIsSix(body.getIsSix());

        ballRepository.save(ball);
        BallCreateResponseDto response = new BallCreateResponseDto();
        response.setBall(ball);
        response.setLegalBalls(ball.getBallType() == BallType.FAIR || ball.getBallType() == BallType.FREE_HIT ? allFairBallsFromOver.size() + 1 : allFairBallsFromOver.size());
        response.setOverCompleted(overCompleted);

        return SuccessResponse.success(SuccessMessages.BALL_CREATED,response);
    }

    public ResponseEntity viewBall(long ballId){
        Ball ball = ballRepository.findById(ballId).orElseThrow(()->new NotFoundException(ErrorMessages.BALL_NOT_FOUND));
        return SuccessResponse.success(SuccessMessages.SUCCESS,ball);
    }
}
