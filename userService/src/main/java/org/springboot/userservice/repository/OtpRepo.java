package org.springboot.userservice.repository;

import org.springboot.userservice.entity.OTP;
import org.springboot.userservice.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public  interface OtpRepo extends JpaRepository<OTP,Integer> {
    Optional<OTP> findByUserAndOtp(Users user, String otpCode);
    Optional<OTP> findTopByUserOrderByIdDesc(Users users);
}
