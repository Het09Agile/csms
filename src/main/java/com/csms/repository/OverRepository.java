package com.csms.repository;

import com.csms.model.Overs;
import com.csms.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OverRepository extends JpaRepository<Overs,Long> {
//    List<Overs> findAllByBowler(Users bowler);
}
