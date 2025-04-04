package com.csms.dto;

import com.csms.model.Ball;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BallCreateResponseDto {
    boolean isOverCompleted;
    int legalBalls;
    Ball ball;
}
