package com.csms.repository;

import com.csms.model.Overs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OverRepository extends JpaRepository<Overs,Long> {
}
