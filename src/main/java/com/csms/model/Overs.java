package com.csms.model;

import com.csms.utils.enums.OverStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter @Getter
@Entity
public class Overs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    int overNo;

    @ManyToOne
    @JoinColumn(name = "bowler",nullable = false)
    Users bowler;

    @ManyToOne
    @JoinColumn(name = "match",nullable = false)
    Match match;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    OverStatus status = OverStatus.CURRENT;

    @OneToMany(targetEntity = Ball.class,mappedBy = "over")
    Set<Ball> balls;
}
