package com.mockeval.repository;

import com.mockeval.entity.Round;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mockeval.entity.Participant;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Participant findByEmail(String email);
    List<Participant> findByActiveTrue();
}