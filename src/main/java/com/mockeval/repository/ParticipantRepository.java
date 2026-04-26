package com.mockeval.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mockeval.entity.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}