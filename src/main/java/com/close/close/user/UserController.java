package com.close.close.user;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * UserController is the controller from the User entity.
 * It is in charge of handling the APIRest interactions with the User.
 */

@RestController
public class UserController {
    private UserRepository repository;;
    private UserModelAssembler assembler;;
    UserController(UserRepository repository, UserModelAssembler assembler){
        this.repository = repository;
        this.assembler = assembler;
    };
    @GetMapping("/users")
    CollectionModel<EntityModel<User>> getAll(){
            List<EntityModel<User>> users = repository.findAll().
            stream().map(assembler::toModel).toList();

            return CollectionModel.of(
                    users,
                    linkTo(methodOn(UserController.class).getAll()).withSelfRel()
            );
    }
}
