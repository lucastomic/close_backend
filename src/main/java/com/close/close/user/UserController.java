package com.close.close.user;

import com.close.close.interest.Interest;
import com.close.close.interest.InterestNotFoundException;
import com.close.close.interest.InterestService;
import com.close.close.security.AuthenticationService;
import com.close.close.user.dto.AuthenticatedUserDTO;
import com.close.close.user.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
/**
 * UserController is the controller from the User entity.
 * It is in charge of handling the APIRest interactions with the User.
 */
@RestController
@RequestMapping("/users")
public class UserController {
    public static final String DELETE_USER = "/";
    public static final String ADD_INTEREST = "/addInterest/{interestName}";
    public static final String REMOVE_INTEREST = "/deleteInterest/{interestName}";
    public static final String GET_USER_INFO = "/getUserInfo";
    public static final String EDIT_PROFILE_PHOTO = "/editPhoto";

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
    private final AuthenticationService AUTH_SERVICE;

    @Autowired
    public UserController (
            UserService userService,
            UserModelAssembler userModelAssembler,
            InterestService interestService,
            AuthenticationService authenticationService

    ) {
        USER_SERVICE = userService;
        USER_MODEL_ASSEMBLER = userModelAssembler;
        INTEREST_SERVICE = interestService;
        AUTH_SERVICE = authenticationService;
    }

    @PutMapping(EDIT_PROFILE_PHOTO)
    public ResponseEntity editPhoto(@RequestBody String photoURL){
        User user = AUTH_SERVICE.getAuthenticated();
        USER_SERVICE.editProfilePhoto(user,photoURL);
        return ResponseEntity.ok().build();
    }


    /**
     * Deletes the user currently authenticated, deleting all their data
     * from the whole application
     * @return Response with No Content status
     */
    @DeleteMapping(DELETE_USER)
    public ResponseEntity<?> delete(){
        Long userId = AUTH_SERVICE.getIdAuthenticated();
        USER_SERVICE.delete(userId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Adds an interest given its name to the user currently authenticated
     * @param interestName interest's name to add to the user
     * @return EntityModel of the user updated
     */
    @PutMapping(ADD_INTEREST)
    public EntityModel<UserDTO> addInterest(@PathVariable String interestName){
        Interest interest = INTEREST_SERVICE.findOrCreate(interestName);
        User user = AUTH_SERVICE.getAuthenticated();
        USER_SERVICE.addInterest(user, interest);
        return USER_MODEL_ASSEMBLER.toModel(new UserDTO(user));
    }

    /**
     * Adds an interest given its name to the user currently authenticated
     * @param interestName inter est's name to add to the user
     * @return EntityModel of the user updated
     */
    @DeleteMapping(REMOVE_INTEREST)
    public EntityModel<UserDTO> removeInterest(@PathVariable String interestName){
        Interest interest = INTEREST_SERVICE.findById(interestName).orElseThrow(InterestNotFoundException::new);
        User user = AUTH_SERVICE.getAuthenticated();
        USER_SERVICE.removeInterest(user, interest);
        return USER_MODEL_ASSEMBLER.toModel(new UserDTO(user));
    }

    /**
     * Returns the information about the user currently authenticated
     * @return Response entity with status code 200, and the current authenticated user in the body
     */
    @GetMapping(GET_USER_INFO)
    public ResponseEntity<EntityModel<UserDTO>> getUserInformation(){
        User user= AUTH_SERVICE.getAuthenticated();
        AuthenticatedUserDTO userDTO = new AuthenticatedUserDTO(user);
        return ResponseEntity.ok(USER_MODEL_ASSEMBLER.toModel(userDTO));
    }

}
