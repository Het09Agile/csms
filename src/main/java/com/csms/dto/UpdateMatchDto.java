package com.csms.dto;

import com.csms.utils.enums.ChooseTo;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter @Setter
public class UpdateMatchDto {
    Date date;

    String venue;

    int totalOvers;

    Set<Long> teams;

    long teamId;

    ChooseTo chooseTo;
}
