package com.mockeval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockeval.entity.Batch;

import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Long> {

    List<Batch> findByActiveTrue();
    Batch findByName(String name);
    Batch findByNameAndStartDate(String name, String startDate);
}