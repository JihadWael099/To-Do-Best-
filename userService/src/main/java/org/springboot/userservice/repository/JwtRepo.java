package org.springboot.userservice.repository;

import org.springboot.userservice.entity.JWT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JwtRepo extends JpaRepository<JWT, Integer> {
}
