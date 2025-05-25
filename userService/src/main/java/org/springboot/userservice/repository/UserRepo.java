package org.springboot.userservice.repository;

import org.springboot.userservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {

    Optional<Users> findByUsername(String name);

    Optional<Users> findById(int id);
}
