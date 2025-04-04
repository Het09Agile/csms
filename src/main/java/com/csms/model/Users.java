package com.csms.model;

import com.csms.utils.enums.Role;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;


@Getter
@Setter
@Entity
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column(unique = true,nullable = false)
    String email;

    @Column
    @JsonIgnore
    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    @Column(nullable = true)
    LocalDate birthDate;

    @OneToMany(mappedBy = "bowler", targetEntity = Overs.class,cascade = CascadeType.ALL)
    @JsonBackReference
    Set<Overs> overs;

    @OneToMany(mappedBy = "batsman", targetEntity = Ball.class,cascade = CascadeType.ALL)
    @JsonBackReference
    Set<Ball> balls;

    @ManyToOne
    @JoinColumn(name = "team",nullable = true)
    @JsonBackReference
    Teams team = null;
}
