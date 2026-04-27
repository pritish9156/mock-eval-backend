package com.mockeval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.mockeval.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    // ✅ GET ONLY ACTIVE USERS
    List<User> findByActiveTrue();
}