package com.close.close.interest;

import com.close.close.user.User;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing interests.
 */
@Service
public class InterestService {

    /**
     * The repository for managing interests.
     */
    private final InterestRepository INTEREST_REPOSITORY;

    /**
     * The assembler for converting interest entities to models.
     */
    private final InterestModelAssembler INTEREST_ASSEMBLER;

    /**
     * Constructs a new instance of the interest service.
     *
     * @param repository The repository for managing interests.
     * @param assembler The assembler for converting interest entities to models.
     */
    public InterestService(InterestRepository repository, InterestModelAssembler assembler) {
        this.INTEREST_REPOSITORY = repository;
        this.INTEREST_ASSEMBLER = assembler;
    }

    /**
     * Returns all interests.
     *
     * @return All interests.
     */
    public List<Interest> findAll() {
        return INTEREST_REPOSITORY.findAll();
    }

    /**
     * Returns the interest with the given name, if it exists.
     *
     * @param name The name of the interest to find.
     * @return The interest with the given name, or empty if not found.
     */
    public Optional<Interest> findById(String name) {
        return INTEREST_REPOSITORY.findById(name);
    }

    /**
     * Saves an interest.
     *
     * @param interest The interest to save.
     * @return The saved interest.
     */
    public Interest save(Interest interest) {
        return INTEREST_REPOSITORY.save(interest);
    }

    /**
     * Deletes the interest with the given name, if it exists.
     *
     * @param name The name of the interest to delete.
     */
    public void deleteById(String name) {
        INTEREST_REPOSITORY.deleteById(name);
    }

    /**
     * Returns all interests belonging to the user with the given ID.
     *
     * @param userId The ID of the user whose interests to find.
     * @return All interests belonging to the user with the given ID.
     */
    public List<Interest> findUserInterests(Long userId) {
        return INTEREST_REPOSITORY.findByUserId(userId);
    }


    /**
     * Returns an interest given its name. If it doesn't exist, it creates an interest
     * with this name and persists it in the database.
     * @param interestName String with the name of the interest to look for or create
     * @return Interest with the given name
     */
    public Interest findOrCreate(String interestName){
        Interest interest = findById(interestName)
                .orElse( new Interest(interestName) );
        INTEREST_REPOSITORY.save(interest);
        return interest;
    }
}

