package com.csms.repository;

import com.csms.model.Match;
import com.csms.model.Teams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match,Long> {
    Optional<Match> findById(long id);
    Page<Match> findAllByTeams(Teams team, Pageable pageable);
}
