package com.csms.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginDto {

    @NotNull
    private String email;

    @NotNull
    private String password;
}
