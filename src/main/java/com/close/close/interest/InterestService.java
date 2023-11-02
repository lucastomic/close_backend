package com.close.close.interest;

import com.close.close.security.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class InterestService {

    private final InterestRepository INTEREST_REPOSITORY;

    @Autowired
    public InterestService(InterestRepository repository ) {
        this.INTEREST_REPOSITORY = repository;
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

    public Set<Interest> getMostPopular(Long amountOfInterests){
        return INTEREST_REPOSITORY.getMostPopular(amountOfInterests);
    }
    public List<Interest> getExcluding(Set<String> exclude, int amount){
        Pageable pageable = PageRequest.of(0, amount);
        return INTEREST_REPOSITORY.getExcluding(exclude, pageable);
    }

    public Interest findOrCreate(String interestName){
        Interest interest = findById(interestName)
                .orElse( new Interest(interestName) );
        INTEREST_REPOSITORY.save(interest);
        return interest;
    }
}

