package com.close.close.user;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * UserController is the controller from the User entity.
 * It is in charge of handling the APIRest interactions with the User.
 */
@RestController
public class UserController {
    /**
     * repository is the user's repository for DB interaction
    */
    private final UserRepository repository;;
    /**
     * assembler is the user's model assembler for converting User models to
     * AIPRest responses
     */
    private final UserModelAssembler assembler;;

    /**
     * Class constructor. It implements dependency injection.
     * @param repository user's repository
     * @param assembler user's model assembler
     */
    UserController(UserRepository repository, UserModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    };

    /**
     * getAll retrieves a CollectionModel with all the application's users.
     * The response is linked to different methods of the APIRest.
     * @return CollectionModel with different links of the APIRest.
     */
    @GetMapping("/users")
    CollectionModel<EntityModel<User>> getAll(){
            List<EntityModel<User>> users = repository.findAll().
            stream().map(assembler::toModel).toList();

            return CollectionModel.of(
                    users,
                    linkTo(methodOn(UserController.class).getAll()).withSelfRel()
            );
    }

    /**
     * getOne returns a user depending on his ID. The response is modeled with the assembler.
     * In case of no user with the ID specified, it's throws a UserNotFoundException (which will
     * be handled by the UserNotFoundAdvice controller advice)
     * @param id long with the ID of the user to be returned
     * @return EntityModel of the user with the ID
     */
    @GetMapping("/users/{id}")
    EntityModel<User> getOne(@PathVariable Long id){
        User user = repository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return assembler.toModel(user);
    }

    /**
     * saveUser saves a user on the database. The user information is passed in the request's body.
     * If the user is saved successfully it returns a OK 200 status and a response with the link for
     * accessing the new User inserted. Also, in the body of the response is the information of the user inserted.
     * @return ResponseEntity with the link to the new employee inserted and the user's information in the request's body
     */
    @PostMapping("/users")
    ResponseEntity<?> saveUser(@RequestBody User newUser){
        EntityModel<User> entityModel = assembler.toModel(repository.save(newUser));
        URI location = this.parseEntityModelToLink(entityModel);
        return ResponseEntity.created(location).body(entityModel);
    }

    /**
     * parseEntityModelToLink parses an entity model into a URI to access to itself inside the API
     * @param entityModel entity model to parse
     * @return URI object with the respective direction
     */
    private URI parseEntityModelToLink(EntityModel<?> entityModel){
        return entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri();
    }
}
