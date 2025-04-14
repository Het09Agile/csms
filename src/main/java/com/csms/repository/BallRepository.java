package com.csms.repository;

import com.csms.model.Ball;
import com.csms.model.Overs;
import com.csms.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BallRepository extends JpaRepository<Ball,Long> {
    List<Ball>  findAllByOver(Overs over);
//    List<Ball>  findAllByBatsman(Users batsman);
}
