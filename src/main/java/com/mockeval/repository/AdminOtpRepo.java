package com.mockeval.repository;

import com.mockeval.entity.AdminOtp;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AdminOtpRepo extends JpaRepository<AdminOtp, Long> {
    AdminOtp findByEmailAndOtp(String email, String otp);
    @Modifying
    @Transactional
    @Query("DELETE FROM AdminOtp a WHERE a.email = :email")
    void deleteByEmail(@Param("email") String email);
}