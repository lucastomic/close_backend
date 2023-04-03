package com.close.close.apirest;


import com.close.close.user.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * UserUtils manages the common logic of the user operations.
 * This is, User type handling (parsings), common User datbase opeartions, etc
 */
public class UserUtils {
    /**
     * repository is the user repository for all DB's interactions
     */
    UserRepository repository;
    /**
     * assembler is the user model assembler for User entity modeling
     */
    UserModelAssembler assembler;

    /**
     * Class constructor
     * @param repository user's repository implementation
     * @param assembler user model assembler
     */
    public UserUtils(UserRepository repository, UserModelAssembler assembler){
       this.repository = repository;
       this.assembler = assembler;
    }
    /**
     * collectionModelFromList takes a list of users, models each one to a EntityModel
     * and pareses all the list into a CollectionModel with a link to all users
     * @param users list of users to parse
     * @return CollectionModel object with each user modeled
     */
    public CollectionModel<EntityModel<User>> collectionModelFromList(List<User> users){
        return CollectionModel.of(
                this.modelUsersList(users),
                linkTo(methodOn(UserController.class).findAll()).withSelfRel()
        );
    }
    /**
     * modelUsersList takes a user's list, model each one to an EntityModel
     * and returns a list with all the EntityModelObjects
     * @param users list of users to parse
     * @return list of EntityModel objects with the users parsed
     */
    public List<EntityModel<User>> modelUsersList(List<User>users){
        return users.stream().map(assembler::toModel).toList();
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

}
