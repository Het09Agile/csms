package com.csms.dto;

import com.csms.model.Teams;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter @Setter
public class CreateMatchDto {
    @NotNull
    Date date;

    @NotNull
    String venue;

    @NotNull
    @Min(1)
    int totalOvers;

    @NotNull
    @Size(min = 2,max = 2)
    Set<Long> teams;
}
