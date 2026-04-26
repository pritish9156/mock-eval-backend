package com.mockeval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import com.mockeval.entity.Participant;
import com.mockeval.repository.ParticipantRepository;

@Service
public class ParticipantService {

    @Autowired
    private ParticipantRepository repo;

    public Participant save(Participant participant) {
        return repo.save(participant);
    }

    public List<Participant> getAll() {
        return repo.findAll();
    }
}