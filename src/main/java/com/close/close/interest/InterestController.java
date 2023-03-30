package com.close.close.interest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * InterestController is the controller from the Interest entity.
 * It is in charge of handling the APIRest interactions with the User.
 */
@RestController
public class InterestController {

    public static final String GET_INTERESTS      = "/interests";
    public static final String GET_INTEREST       = "/interests/{interestId}";
    public static final String POST_INTEREST      = "/interests";
    public static final String GET_USER_INTERESTS = "/users/{userId}/interests";

    private final InterestService INTEREST_SERVICE;
    private final InterestModelAssembler INTEREST_MODEL_ASSEMBLER;


    @Autowired
    InterestController(InterestService interestService,
                       InterestModelAssembler interestModelAssembler){
        this.INTEREST_SERVICE = interestService;
        this.INTEREST_MODEL_ASSEMBLER = interestModelAssembler;
    };


    /**
     * Retrieves a CollectionModel with all the application's interest.
     * The response is linked to different methods of the APIRest.
     * @return CollectionModel with different links of the APIRest.
     */
    @GetMapping(GET_INTERESTS)
    public CollectionModel<EntityModel<Interest>> findAll(){
        List<EntityModel<Interest>> modelinterests = INTEREST_SERVICE
                .findAll().stream().map(INTEREST_MODEL_ASSEMBLER::toModel).toList();

        return CollectionModel.of(
                modelinterests,
                linkTo(methodOn(InterestController.class).findAll()).withSelfRel()
        );
    }

    /**
     * Returns am interest depending on his ID. The response is modeled with the assembler.
     * In case of no interest with the ID specified, it's throws an InterestNotFoundException (which will
     * be handled by the UserNotFoundAdvice controller advice)
     * @param name The id of the interest to be returned
     * @return EntityModel of the interest with the name specified
     */
    @GetMapping(GET_INTEREST)
    public EntityModel<Interest> findById(@PathVariable String name){
        Interest interest = INTEREST_SERVICE.findById(name)
                .orElseThrow(InterestNotFoundException::new);
        return INTEREST_MODEL_ASSEMBLER.toModel(interest);
    }

    /**
     * Method for saving an interest on the database.
     * @return ResponseEntity with the link to the new interest
     * in the location header and the interest's information in the request's body
     */
    @PostMapping(POST_INTEREST)
    public ResponseEntity<?> create(@RequestBody Interest newInterest){
        INTEREST_SERVICE.save(newInterest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newInterest);
    }

    /**
     * Method for finding a users interests.
     * @param userId The if of the user whose interests are going to be retrieved
     * @return The user interests.
     */
    @GetMapping(GET_USER_INTERESTS)
    public CollectionModel<EntityModel<Interest>> findUserInterests(@PathVariable Long userId) {
        List<EntityModel<Interest>> modelInterests = INTEREST_SERVICE
                .findUserInterests(userId).stream().map(INTEREST_MODEL_ASSEMBLER::toModel).toList();

        return CollectionModel.of(
                modelInterests,
                linkTo(methodOn(InterestController.class).findAll()).withSelfRel()
        );
    }
}
