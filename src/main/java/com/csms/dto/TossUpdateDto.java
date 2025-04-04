package com.csms.dto;

import com.csms.utils.enums.ChooseTo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TossUpdateDto {

    @NotNull
    long matchId;

    @NotNull
    long teamId;

    @NotNull
    ChooseTo chooseTo;
}
