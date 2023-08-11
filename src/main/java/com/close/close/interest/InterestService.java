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
    private final AuthenticationService AUTH_SERVICE;

    @Autowired
    public InterestService(InterestRepository repository, AuthenticationService AUTH_SERVICE) {
        this.INTEREST_REPOSITORY = repository;
        this.AUTH_SERVICE = AUTH_SERVICE;
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

    public List<Interest> getNotSelectedInterests(int amountOfInterests){
        Long userId = AUTH_SERVICE.getIdAuthenticated();
        Pageable pageable = PageRequest.of(0, amountOfInterests);
        return INTEREST_REPOSITORY.getNotSelectedInterests(userId, pageable);
    }

    public Interest findOrCreate(String interestName){
        Interest interest = findById(interestName)
                .orElse( new Interest(interestName) );
        INTEREST_REPOSITORY.save(interest);
        return interest;
    }
}

