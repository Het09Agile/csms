package com.csms.repository;

import com.csms.model.Teams;
import com.csms.model.Users;
import com.csms.utils.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByRole(Role role);
    Optional<Users> findById(Long id);
    Optional<Users> findByEmail(String email);
    Optional<Users> findByIdAndRole(long id, Role role);
    Optional<Users> findByIdAndTeam(long id, Teams team);
}
