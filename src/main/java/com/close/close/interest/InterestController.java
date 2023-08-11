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
import java.util.Set;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * InterestController is the controller from the Interest entity.
 * It is in charge of handling the APIRest interactions with the User.
 */
@RestController
@RequestMapping("/interests")
public class InterestController {
    public static final String POST_INTEREST      = "";
    public static final String GET_MOST_POPULAR      = "/getMostPopulars/{amountOfInterests}";
    public static final String GET_NOT_SELECTED      = "/getNotSelected/{amountOfInterests}";
    public static final String DELETE_INTEREST      = "/{interestName}";

    private final InterestService INTEREST_SERVICE;


    @Autowired
    InterestController(InterestService interestService,
                       InterestModelAssembler interestModelAssembler,
                       AuthenticationService authenticationService){
        this.INTEREST_SERVICE = interestService;
    };

    @GetMapping(GET_MOST_POPULAR)
    public ResponseEntity<?> getMostPopular(@PathVariable Long amountOfInterests){
        Set<Interest> body =  INTEREST_SERVICE.getMostPopular(amountOfInterests);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
    @GetMapping(GET_NOT_SELECTED)
    public ResponseEntity<?> getNotSelectedInterests(@PathVariable int amountOfInterests){
        List<Interest> body =  INTEREST_SERVICE.getNotSelectedInterests(amountOfInterests);
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
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
     * Deletes an interest, given its name
     * @param interestName name of the interest to delete
     * @return no contente response
     */
    @DeleteMapping(DELETE_INTEREST)
    public ResponseEntity<?> delete(@PathVariable String interestName){
        INTEREST_SERVICE.deleteById(interestName);
        return ResponseEntity.noContent().build();
    }


}
