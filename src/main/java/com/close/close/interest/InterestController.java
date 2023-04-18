package com.close.close.interest;

import com.close.close.security.AuthenticationResponse;
import com.close.close.security.AuthenticationService;
import com.close.close.user.User;
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
@RequestMapping("/interests")
public class InterestController {

    public static final String GET_INTERESTS      = "";
    public static final String GET_INTEREST       = "/{interestId}";
    public static final String POST_INTEREST      = "";
    public static final String GET_USER_INTERESTS = "/currentUser";

    private final InterestService INTEREST_SERVICE;
    private final AuthenticationService AUTH_SERVICE;
    private final InterestModelAssembler INTEREST_MODEL_ASSEMBLER;


    @Autowired
    InterestController(InterestService interestService,
                       InterestModelAssembler interestModelAssembler,
                       AuthenticationService authenticationService){
        this.INTEREST_SERVICE = interestService;
        this.INTEREST_MODEL_ASSEMBLER = interestModelAssembler;
        this.AUTH_SERVICE = authenticationService;
    };


    /**
     * Retrieves a CollectionModel with all the application's interest.
     * The response is linked to different methods of the APIRest.
     * @return CollectionModel with different links of the APIRest.
     */
    //TODO: Must need an Admin authorization
    @GetMapping(GET_INTERESTS)
    public CollectionModel<EntityModel<Interest>> findAll(){
        List<EntityModel<Interest>> modelInterests = INTEREST_SERVICE
                .findAll().stream().map(INTEREST_MODEL_ASSEMBLER::toModel).toList();

        return CollectionModel.of(
                modelInterests,
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
    //TODO: Must need an Admin authorization
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
     * For example, this would be a correct request body:
     * {
     *     "name":"Chess"
     * }
     */
    @PostMapping(POST_INTEREST)
    public ResponseEntity<?> create(@RequestBody Interest newInterest){
        INTEREST_SERVICE.save(newInterest);
        return ResponseEntity.status(HttpStatus.CREATED).body(newInterest);
    }


    /**
     * Finds the interests of the currently authenticated user.
     * @return Collection model with all the user interests
     */
    @GetMapping(GET_USER_INTERESTS)
    public CollectionModel<EntityModel<Interest>> findUserInterests() {

        Long userId = AUTH_SERVICE.getIdAuthenticated();

        List<EntityModel<Interest>> modelInterests = INTEREST_SERVICE
                .findUserInterests(userId).stream().map(INTEREST_MODEL_ASSEMBLER::toModel).toList();

        return CollectionModel.of(
                modelInterests,
                linkTo(methodOn(InterestController.class).findAll()).withSelfRel()
        );
    }

}
