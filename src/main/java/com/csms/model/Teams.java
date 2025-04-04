package com.csms.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter @Getter
@Entity
public class Teams {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = true)
    String country;

    @OneToOne(targetEntity = Users.class, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    Users manager;

    @ManyToMany(mappedBy = "teams")
    @JsonBackReference
    private Set<Match> matches;

    @OneToMany(mappedBy = "team", targetEntity = Users.class,cascade = CascadeType.ALL)
    @JsonManagedReference
    Set<Users> players;

}
