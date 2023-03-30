package com.close.close.user;

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
 * UserController is the controller from the User entity.
 * It is in charge of handling the APIRest interactions with the User.
 */
@RestController
public class UserController {

    public static final String GET_ALL_USERS     = "/users";
    public static final String GET_USER_BY_ID    = "/users/{userId}";
    public static final String CREATE_USER       = "/users";
    public static final String DELETE_USER_BY_ID = "/user/{userId}";

    /**
     * repository is the user's repository for DB interaction
    */
    private final UserService USER_SERVICE;

    /**
     * assembler is the user's model assembler for converting User models to
     * AIPRest responses
     */
    private UserModelAssembler USER_MODEL_ASSEMBLER;


    public UserController (UserService userService,
                           UserModelAssembler userModelAssembler) {
        USER_SERVICE = userService;
        USER_MODEL_ASSEMBLER = userModelAssembler;
    }


    /**
     * getAll retrieves a CollectionModel with all the application's users.
     * The response is linked to different methods of the APIRest.
     * @return CollectionModel with different links of the APIRest.
     */
    @GetMapping(GET_ALL_USERS)
    public CollectionModel<EntityModel<User>> findAll() {
        List<EntityModel<User>> modelUsers = USER_SERVICE.getAll().stream().map(USER_MODEL_ASSEMBLER::toModel).toList();
        return CollectionModel.of(
                modelUsers,
                linkTo(methodOn(UserController.class).findAll()).withSelfRel()
        );
    }

    /**
     * getOne returns a user depending on his ID. The response is modeled with the assembler.
     * In case of no user with the ID specified, it's throws a UserNotFoundException (which will
     * be handled by the UserNotFoundAdvice controller advice)
     * @param userId long with the ID of the user to be returned
     * @return EntityModel of the user with the ID
     */
    @GetMapping(GET_USER_BY_ID)
    public EntityModel<User> findById(@PathVariable Long userId){
        User user = USER_SERVICE.findById(userId);
        return USER_MODEL_ASSEMBLER.toModel(user);
    }

    /**
     * saveUser saves a user on the database. The user information is passed in the request's body.
     * If the user is saved successfully it returns an OK 200 status and a response with the link for
     * accessing the new User inserted. Also, in the body of the response is the information of the user inserted.
     * Before saving the User data, its password is encoded by the PasswordEncoder and then data is saved
     * @return ResponseEntity with the link to the new employee inserted and the user's information in the request's body
     */
    @PostMapping(CREATE_USER)
    public ResponseEntity<EntityModel<User>> create(@RequestBody User newUser){
        User user = USER_SERVICE.create(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(USER_MODEL_ASSEMBLER.toModel(user));
    }

    /**
     * deleteUserById deletes the user whose ID is passed by parameter
     * @param userId id of the user to be removed
     * @return
     */
    @DeleteMapping(DELETE_USER_BY_ID)
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        try {
            USER_SERVICE.delete(userId);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * login signs in the user.
     * It expects the user credentials and returns a signed token if
     * the credentials are valid. If they're not, it returns a
     * InvalidCredentialsException.
     * @param credentials credentials to sign in
     * @return ResponseEntity with 200 status code and token in the body
     */
    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody UserCredentials credentials){
        //TODO:IMPLEMENT
        return ResponseEntity.ok("Ok");
    }

    //TODO: make
    /** @PostMapping("/logout")
    public ResponseEntity<?> login(@RequestBody User newUser){
        RestSaver<User> saver = new RestSaver<User>(repository, assembler);
        return saver.saveEntity(newUser);
    }
    **/
}
