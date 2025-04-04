package com.csms.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class AddTeamPlayers {
    Set<Long> addPlayers;

    Set<Long> removePlayers;

    long teamId;
}
