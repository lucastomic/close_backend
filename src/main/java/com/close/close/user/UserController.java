package com.close.close.user;

import com.close.close.apirest.RestSaver;
import com.close.close.apirest.UserUtils;
import com.close.close.security.authentication.TokenService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * UserController is the controller from the User entity.
 * It is in charge of handling the APIRest interactions with the User.
 */
@RestController
public class UserController {

    public static final String GET_USERS  = "/users";
    public static final String GET_USER   = "/users/{userId}";
    public static final String POST_USER  = "/users";
    public static final String DELETE_USER = "/user/{userId}";

    /** entityManager makes the SQL queries */
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** repository is the user's repository for DB interaction */
    private final UserRepository REPOSITORY;

    /** Assembler is the user's model assembler for converting User models to
     * AIPRest responses */
    private final UserModelAssembler ASSEMBLER;


    /**
     * Class constructor. It implements dependency injection.
     * @param repository user's repository
     * @param assembler user's model assembler
     */
    UserController(UserRepository repository, UserModelAssembler assembler){
        this.REPOSITORY = repository;
        this.ASSEMBLER = assembler;
    };


    /**
     * getAll retrieves a CollectionModel with all the application's users.
     * The response is linked to different methods of the APIRest.
     * @return CollectionModel with different links of the APIRest.
     */
    @GetMapping(GET_USERS)
    public CollectionModel<EntityModel<User>> getAll(){
            UserUtils utils = new UserUtils(REPOSITORY, ASSEMBLER);
            List<User> allUsers = REPOSITORY.findAll();
            return utils.collectionModelFromList(allUsers);
    }

    /**
     * getOne returns a user depending on his ID. The response is modeled with the assembler.
     * In case of no user with the ID specified, it's throws a UserNotFoundException (which will
     * be handled by the UserNotFoundAdvice controller advice)
     * @param userId long with the ID of the user to be returned
     * @return EntityModel of the user with the ID
     */
    @GetMapping(GET_USER)
    EntityModel<User> getOne(@PathVariable Long userId){
        UserUtils userUtils = new UserUtils(REPOSITORY, ASSEMBLER);
        User user = userUtils.findOrThrow(userId);
        return ASSEMBLER.toModel(user);
    }

    /**
     * saveUser saves a user on the database. The user information is passed in the request's body.
     * If the user is saved successfully it returns an OK 200 status and a response with the link for
     * accessing the new User inserted. Also, in the body of the response is the information of the user inserted.
     * Before saving the User data, its password is encoded by the PasswordEnconder and then data is saved
     * @return ResponseEntity with the link to the new employee inserted and the user's information in the request's body
     */
    @PostMapping(POST_USER)
    ResponseEntity<?> create(@RequestBody User newUser){
        RestSaver<User> saver = new RestSaver<User>(REPOSITORY, ASSEMBLER);
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        return saver.saveEntity(newUser);
    }

    /**
     * deleteUserById deletes the user whose ID is passed by parameter
     * @param userId id of the user to be removed
     * @return
     */
    //TODO: REVIEW
    @DeleteMapping(DELETE_USER)
    public ResponseEntity<?> delete(@PathVariable Long userId) {
        try {
            User userToDelete = REPOSITORY.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));
            REPOSITORY.delete(userToDelete);
            return ResponseEntity.ok().build();
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
        User user = this.getUserFromPhone(credentials.getPhone());

        if(passwordEncoder.matches(credentials.getPassword(), user.getPassword())){
            TokenService tokenService = new TokenService();
            return ResponseEntity.ok(tokenService.generateToken(user));
        }else{
            throw new InvalidCredentialsException();
        }
    }

    /**
     * getUserFromPhone gets a user given his phone number.
     * @param phone user's phone number
     * @return User object with the phone number indicated
     */
    private User getUserFromPhone(String phone){
        String queryString = "SELECT u FROM User u WHERE u.phone = :phone";
        Query query = entityManager.createQuery(queryString).setParameter("phone",phone);
        return (User) query.getResultList().get(0);
    }

    //TODO: make
    @PostMapping("/logout")
    ResponseEntity<?> login(@RequestBody User newUser){
        RestSaver<User> saver = new RestSaver<User>(REPOSITORY, ASSEMBLER);
        return saver.saveEntity(newUser);
    }
}
