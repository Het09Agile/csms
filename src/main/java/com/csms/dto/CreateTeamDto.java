package com.csms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateTeamDto {

    @NotNull
    String name;

    String country;

    long manager;
}
