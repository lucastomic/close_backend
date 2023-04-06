package com.close.close.user;

import com.close.close.apirest.UserUtils;
import com.close.close.interest.Interest;
import com.close.close.interest.InterestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * UserController is the controller from the User entity.
 * It is in charge of handling the APIRest interactions with the User.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    public static final String GET_ALL_USERS     = "";
    public static final String GET_USER_BY_ID    = "/{userId}";
    public static final String DELETE_USER_BY_ID = "/{userId}";
    public static final String ADD_INTEREST = "/addInterest/{interestName}";

    /**
     * repository is the user's repository for DB interaction
    */
    private final UserService USER_SERVICE;

    /**
     * assembler is the user's model assembler for converting User models to
     * AIPRest responses
     */
    private final UserModelAssembler USER_MODEL_ASSEMBLER;

    private final InterestService INTEREST_SERVICE;

    @Autowired
    public UserController (
            UserService userService,
            UserModelAssembler userModelAssembler,
            InterestService interestService

    ) {
        USER_SERVICE = userService;
        USER_MODEL_ASSEMBLER = userModelAssembler;
        INTEREST_SERVICE = interestService;
    }


    /**
     * getAll retrieves a CollectionModel with all the application's users.
     * The response is linked to different methods of the APIRest.
     * @return CollectionModel with different links of the APIRest.
     */
    @GetMapping(GET_ALL_USERS)
    public CollectionModel<EntityModel<User>> findAll() {
        UserUtils utils = new UserUtils(null, USER_MODEL_ASSEMBLER);
        List<User> users = USER_SERVICE.getAll();
        List<EntityModel<User>> modelUsers = utils.modelUsersList(users);
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
     * deleteUserById deletes the user whose ID is passed by parameter
     * @param userId id of the user to be removed
     * @return Response with No Content status
     */
    @DeleteMapping(DELETE_USER_BY_ID)
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        USER_SERVICE.delete(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adds an interest given its name to the user currently authenticated
     * @param interestName interest's name to add to the user
     * @return EntityModel of the user updated
     */
    //TODO: still not working
    @PutMapping(ADD_INTEREST)
    public EntityModel<User> addInterest(@PathVariable String interestName){
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Interest interest = INTEREST_SERVICE.findOrCreate(interestName);
        USER_SERVICE.addInterest(principal,interest);
        return USER_MODEL_ASSEMBLER.toModel(principal);
    }

}
