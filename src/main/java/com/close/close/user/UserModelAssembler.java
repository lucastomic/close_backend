package com.close.close.user;

import com.close.close.duck.DuckController;
import com.close.close.interest.InterestController;
import com.close.close.location.LocationController;
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
                //User Controller Links
                linkTo(methodOn(UserController.class).getAll()).withRel("users"),
                linkTo(methodOn(UserController.class).getOne(user.getId())).withSelfRel(),
                //Duck Controller Links
                linkTo(methodOn(DuckController.class).sendDuck(user.getId(), 0L)).withRel("sendDuck") ,
                linkTo(methodOn(DuckController.class).getDucksReceived(user.getId())).withRel("getDucksReceived")
                //Location Controller Links
                //linkTo(methodOn(LocationController.class).searchUsers(user.getId(), 10)).withRel("closeUsers")
        );
    }
}
