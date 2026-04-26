package com.mockeval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockeval.entity.Batch;

public interface BatchRepository extends JpaRepository<Batch, Long> {
}