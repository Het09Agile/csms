package com.csms.repository;

import com.csms.model.Teams;
import com.csms.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TeamRepository extends JpaRepository<Teams,Long> {
    Optional<Teams> findById(long id);
//    List<Teams> findAllById(List<Long> ids);
    Optional<Teams> findByManager(Users users);
}
