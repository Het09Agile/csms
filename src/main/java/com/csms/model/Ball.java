package com.csms.model;

import com.csms.utils.enums.BallType;
import com.csms.utils.enums.ExtraRunType;
import com.csms.utils.enums.WicketType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
public class Ball {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    @JoinColumn(name = "batsman",nullable = false)
    @JsonBackReference
    Users batsman;

    @ManyToOne
    @JoinColumn(name = "over",nullable = false)
    @JsonBackReference
    Overs over;

    @Column(name = "ball_no",nullable = false)
    int ballOfOver;

    @Min(0)
    @Max(8)
    @Column
    int run=0;

    @Min(0)
    @Max(6)
    @Column
    int extraRun=0;

    @Enumerated(EnumType.STRING)
    @Column
    ExtraRunType extraRunType=null;

    @Enumerated(EnumType.STRING)
    @Column
    WicketType wicketType = null;

    @Column
    String commentary;

    @Enumerated(EnumType.STRING)
    @Column
    BallType ballType = BallType.FAIR;

    @Column
    Boolean isFour;

    @Column
    Boolean isSix;
}
