package com.csms.dto;

import com.csms.model.Overs;
import com.csms.model.Users;
import com.csms.utils.enums.BallType;
import com.csms.utils.enums.ExtraRunType;
import com.csms.utils.enums.WicketType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateBallDto {
    @NotNull
    long batsmanId;

    @NotNull
    long overId;

    @Min(0)
    @Max(8)
    int run=0;

    @Min(0)
    @Max(6)
    int extraRun=0;

    ExtraRunType extraRunType;

    WicketType wicketType;

    String commentary;

    @NotNull
    BallType ballType = BallType.FAIR;

    Boolean isFour;
    Boolean isSix;
}
