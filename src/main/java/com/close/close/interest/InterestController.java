package com.close.close.interest;

import com.close.close.apirest.RestSaver;
import com.close.close.user.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * InterestController is the controller from the Interest entity.
 * It is in charge of handling the APIRest interactions with the User.
 */
@RestController
public class InterestController {
    /**
     * repository is the user's repository for DB interaction
     */
    private final InterestRepository repository;;
    /**
     * assembler is the user's model assembler for converting User models to
     * AIPRest responses
     */
    private final InterestModelAssembler assembler;;

    /**
     * Class constructor. It implements dependency injection.
     * @param repository user's repository
     * @param assembler user's model assembler
     */
    InterestController(InterestRepository repository, InterestModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    };
    /**
     * getAll retrieves a CollectionModel with all the application's interest.
     * The response is linked to different methods of the APIRest.
     * @return CollectionModel with different links of the APIRest.
     */
    @GetMapping("/interest")
    CollectionModel<EntityModel<Interest>> getAll(){
        List<EntityModel<Interest>> interest = repository.findAll().
                stream().map(assembler::toModel).toList();

        return CollectionModel.of(
                interest,
                linkTo(methodOn(InterestController.class).getAll()).withSelfRel()
        );
    }

    /**
     * getOne returns am interest depending on his ID. The response is modeled with the assembler.
     * In case of no interest with the ID specified, it's throws an InterestNotFoundException (which will
     * be handled by the UserNotFoundAdvice controller advice)
     * @param name string with the ID of the interest to be returned
     * @return EntityModel of the interest with the name specified
     */
    @GetMapping("/interest/{name}")
    EntityModel<Interest> getOne(@PathVariable String name){
        Interest interest = repository.findById(name)
                .orElseThrow(InterestNotFoundException::new);
        return assembler.toModel(interest);
    }

    /**
     * saveInterest saves an interest on the database. The interest
     * information is passed in the request's body.
     * @return ResponseEntity with the link to the new interest
     * in the location header and the interest's information in the request's body
     */
    @PostMapping("/interest")
    ResponseEntity<?> saveInterest(@RequestBody Interest newInterest){
        RestSaver<Interest> saver = new RestSaver<Interest>(repository,assembler);
        return saver.saveEntity(newInterest);
    }




}
