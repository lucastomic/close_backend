package com.close.close.interest;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InterestService {

    private final InterestRepository INTEREST_REPOSITORY;
    private final InterestModelAssembler INTEREST_ASSEMBLER;

    public InterestService(InterestRepository repository, InterestModelAssembler assembler) {
        this.INTEREST_REPOSITORY = repository;
        this.INTEREST_ASSEMBLER = assembler;
    }

    public List<Interest> findAll() {
        return INTEREST_REPOSITORY.findAll();
    }

    public Optional<Interest> findById(String name) {
        return INTEREST_REPOSITORY.findById(name);
    }

    public Interest save(Interest interest) {
        return INTEREST_REPOSITORY.save(interest);
    }

    public void deleteById(String name) {
        INTEREST_REPOSITORY.deleteById(name);
    }

    public List<Interest> findUserInterests(Long userId) {
        return INTEREST_REPOSITORY.findByUserId(userId);
    }
}
