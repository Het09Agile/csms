package com.csms.model;

import com.csms.utils.enums.ChooseTo;
import com.csms.utils.enums.MatchStatus;
import com.csms.utils.messages.ErrorMessages;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter @Setter
@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    Date date;

    @Column(nullable = false)
    String venue;

    @Column(nullable = false)
    @Min(value = 1, message = ErrorMessages.MIN_OVER)
    int totalOvers;

    @ManyToMany
    @JoinTable(
            name = "match_team",
            joinColumns = @JoinColumn(name = "matchId"),
            inverseJoinColumns = @JoinColumn(name = "teamId")
    )
    @JsonBackReference
//    @JsonIgnoreProperties({"matches","players"})
    private Set<Teams> teams;

    @ManyToOne(targetEntity = Teams.class)
    @JsonBackReference
    Teams toss;

    @Column
    @Enumerated(EnumType.STRING)
    ChooseTo optTo;

    @Column
    @Enumerated(EnumType.STRING)
    MatchStatus status = MatchStatus.UPCOMING ;

    @OneToMany(mappedBy = "match")
    @JsonBackReference
    Set<Overs> overs;

    @Column
    boolean inningsBreak = false;
}
