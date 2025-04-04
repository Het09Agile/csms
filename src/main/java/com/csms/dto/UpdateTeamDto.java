package com.csms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  UpdateTeamDto {

    String name;

    String country;

    long manager;

    long teamId;

}
