package com.mockeval.repository;

import com.mockeval.entity.AdminApprovalToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminApprovalTokenRepo extends JpaRepository<AdminApprovalToken, Long> {
    AdminApprovalToken findByToken(String token);
}