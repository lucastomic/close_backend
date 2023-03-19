package com.close.close.user;


import com.close.close.apirest.RestSaver;
import com.close.close.duck.Duck;
import com.close.close.duck.DuckRepository;
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

    private final DuckRepository duckRepo;

    /**
     * Class constructor. It implements dependency injection.
     * @param repository user's repository
     * @param assembler user's model assembler
     */
    UserController(UserRepository repository, UserModelAssembler assembler, DuckRepository duckRepo){
        this.repository = repository;
        this.duckRepo = duckRepo;
        this.assembler = assembler;
    };

    /**
     * modelUsersList takes a user's list, model each one to an EntityModel
     * and returns a list with all the EntityModelObjects
     * @param users list of users to parse
     * @return list of EntityModel objects with the users parsered
     */
    private List<EntityModel<User>> modelUsersList(List<User>users){
       return users.stream().map(assembler::toModel).toList();
    }

    /**
     * collectionModelFromList takes a list of users, models each one to a EntityModel
     * and pareses all the list into a CollectionModel with a link to all users
     * @param users list of users to parse
     * @return CollectionModel object with each user modeled
     */
    CollectionModel<EntityModel<User>> collectionModelFromList(List<User> users){
        return CollectionModel.of(
                this.modelUsersList(users),
                linkTo(methodOn(UserController.class).getAll()).withSelfRel()
        );
    }
    /**
     * getAll retrieves a CollectionModel with all the application's users.
     * The response is linked to different methods of the APIRest.
     * @return CollectionModel with different links of the APIRest.
     */
    @GetMapping("/users")
    CollectionModel<EntityModel<User>> getAll(){
            List<User> allUsers = repository.findAll();
            return this.collectionModelFromList(allUsers);
    }

    /**
     * findOrThrow looks a user for his id, and if it doesn't exist it throws a
     * UserNotFoundException
     * @param id id which is looked for
     * @return User with the ID
     */
    public User findOrThrow(Long id){
        return repository.findById(id).orElseThrow(()->new UserNotFoundException(id));
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
        User user = this.findOrThrow(id);
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
        RestSaver<User> saver = new RestSaver<User>(repository, assembler);
        return saver.saveEntity(newUser);
    }

    /**
     * parseEntityModelToLink parses an entity model into a URI to access to itself inside the API
     * @param entityModel entity model to parse
     * @return URI object with the respective direction
     */
    private URI parseEntityModelToLink(EntityModel<?> entityModel){
        return entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri();
    }


    //TODO: Should we move this endpoint to a DuckController?
    /**
     * sendDuck sends a duck from a user to another one and save the transaction on the database.
     * The id of the transmitter and the receiver are sent by path.
     * @param transmitterId id from the transmitter
     * @param receiverId id from the receiver
     * @return ResponseEntity with a 200 status code and a CollectionModel with the implied users in the body
     */
    @PostMapping("/sendDuck")
    ResponseEntity sendDuck(@RequestParam Long transmitterId, @RequestParam Long receiverId) {
        User transmitter = this.findOrThrow(transmitterId);
        User receiver = this.findOrThrow(receiverId);
        Duck duckSent = new Duck(transmitter, receiver);
        duckRepo.save(duckSent);
        List<User> usersList = List.of(receiver, transmitter);
        CollectionModel<EntityModel<User>> body = this.collectionModelFromList(usersList);
        return ResponseEntity.ok().body(body);
    }

}
