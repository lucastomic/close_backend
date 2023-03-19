package com.close.close.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * UserModelAssembler is in charge of modeling the User entity for APIRest responses.
 */
//TODO: add link to send duck to this user
@Component
public class UserModelAssembler  implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(
                user,
                linkTo(methodOn(UserController.class).getAll()).withRel("users"),
                linkTo(methodOn(UserController.class).getOne(user.getId())).withSelfRel()
        );
    }

}
