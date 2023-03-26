package com.close.close.user;

import com.close.close.apirest.RestSaver;
import com.close.close.apirest.UserUtils;
import com.close.close.authentication.TokenService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.jasypt.encryption.pbe.PBEStringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

/**
 * UserController is the controller from the User entity.
 * It is in charge of handling the APIRest interactions with the User.
 */
@RestController
public class UserController {

    /**
     * entityManager makes the SQL queries
     */
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * repository is the user's repository for DB interaction
    */
    private final UserRepository REPOSITORY;

    /**
     * assembler is the user's model assembler for converting User models to
     * AIPRest responses
     */
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
    @GetMapping("/users")
    public CollectionModel<EntityModel<User>> getAll(){
            UserUtils utils = new UserUtils(REPOSITORY, ASSEMBLER);
            List<User> allUsers = REPOSITORY.findAll();
            return utils.collectionModelFromList(allUsers);
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
        UserUtils userUtils = new UserUtils(REPOSITORY, ASSEMBLER);
        User user = userUtils.findOrThrow(id);
        return ASSEMBLER.toModel(user);
    }



    /**
     * saveUser saves a user on the database. The user information is passed in the request's body.
     * If the user is saved successfully it returns an OK 200 status and a response with the link for
     * accessing the new User inserted. Also, in the body of the response is the information of the user inserted.
     * Before saving the User data, its password is encrypted using a Password-Based Encryption (PBE)
     * @return ResponseEntity with the link to the new employee inserted and the user's information in the request's body
     */
    @PostMapping("/users")
    ResponseEntity<?> saveUser(@RequestBody User newUser){
        RestSaver<User> saver = new RestSaver<User>(REPOSITORY, ASSEMBLER);
        PBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        String encryptedPassword = encryptor.encrypt(newUser.getPassword());
        newUser.setPassword(encryptedPassword);
        return saver.saveEntity(newUser);
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody UserCredentials credentials){
        String queryString = "SELECT u FROM User u WHERE u.phone = :phone";
        Query query = entityManager.createQuery(queryString).setParameter("phone",credentials.getPhone());
        User user = (User) query.getResultList().get(0);

        PBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        String encryptedPassword = encryptor.encrypt(credentials.getPassword());

        if(Objects.equals(encryptedPassword, user.getPassword())){
            TokenService tokenService = new TokenService();
            return ResponseEntity.ok(tokenService.generateToken(user));
        }else{
            return (ResponseEntity<?>) ResponseEntity.badRequest();
        }

    }

    @PostMapping("/logout")
    ResponseEntity<?> login(@RequestBody User newUser){
        RestSaver<User> saver = new RestSaver<User>(REPOSITORY, ASSEMBLER);
        PBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        String encryptedPassword = encryptor.encrypt(newUser.getPassword());
        newUser.setPassword(encryptedPassword);
        return saver.saveEntity(newUser);
    }
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id) {
        try {
            User userToDelete = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException("User not found with id " + id));
            String encryptedPassword = passwordEncryptor.encrypt(userToDelete.getPassword());
            userToDelete.setPassword(encryptedPassword);
            userRepository.delete(userToDelete);
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
