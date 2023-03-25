package com.close.close.user;

import com.close.close.duck.DuckController;
import org.jetbrains.annotations.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


/**
 * UserModelAssembler is in charge of modeling the User entity for APIRest responses.
 */
@Component
public class UserModelAssembler  implements RepresentationModelAssembler<User, EntityModel<User>> {
    @Override
    public @NotNull EntityModel<User> toModel(@NotNull User user) {
        return EntityModel.of(
                user,
                linkTo(methodOn(UserController.class).getAll()).withRel("users"),
                linkTo(methodOn(UserController.class).getOne(user.getId())).withSelfRel(),
                linkTo(methodOn(DuckController.class).sendDuck(0L, user.getId())).withRel("sendDuck") ,
                linkTo(methodOn(DuckController.class).getDucksReceived(user.getId())).withRel("getDucksReceived")
        );
    }

}
