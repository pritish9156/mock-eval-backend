package com.mockeval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockeval.entity.Round;

import java.util.List;

public interface RoundRepository extends JpaRepository<Round, Long> {

    List<Round> findByActiveTrue();
}