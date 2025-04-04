package com.csms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateOverDto {
    @NotNull
    long bowlerId;

    @NotNull
    long matchId;
}
