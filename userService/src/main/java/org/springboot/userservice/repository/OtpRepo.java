package org.springboot.userservice.repository;

import org.springboot.userservice.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public  interface OtpRepo extends JpaRepository<OTP,Integer> {
}
