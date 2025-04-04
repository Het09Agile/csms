package com.csms.dto;

import lombok.Getter;
import java.time.LocalDate;

@Getter
public class UpdateUserDto {

    private String firstName;

    private String lastName;

    private LocalDate birthDate;

}
