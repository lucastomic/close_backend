package com.close.close.user;

import com.close.close.duck.DuckController;
import com.close.close.location.Location;
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
    private final String GETALLREL = "allUsers";
    private final String SENDDUCKREL = "sendDuck";
    private final String DUCKSRECEIVEDREL = "ducksReceived";
    private final String SENDLOCATIONREL = "sendLocation";
    private final String CLOSEUSERSREL = "closeUsers";
    private final String ADDINTEREST = "addInterest";
    private final String GETUSERINFO ="userInfo";

    @Override
    public @NotNull EntityModel<User> toModel(@NotNull User user) {
        return EntityModel.of(
                user,
                //User Controller Links
                linkTo(methodOn(UserController.class)
                        .findById(user.getId())
                ).withSelfRel(),

                linkTo(methodOn(UserController.class)
                        .findAll()
                ).withRel(GETALLREL),

                linkTo(methodOn(UserController.class)
                        .addInterest("Chess")
                ).withRel(ADDINTEREST),
                //Duck Controller Links
                linkTo(methodOn(DuckController.class)
                        .sendDuck(user.getId())
                ).withRel(SENDDUCKREL),

                linkTo(methodOn(DuckController.class)
                        .getDucksReceived()
                ).withRel(DUCKSRECEIVEDREL),

                //Location Controller Links
                linkTo(methodOn(LocationController.class)
                        .sendLocation(user.getId(), new Location(0, 0))
                ).withRel(SENDLOCATIONREL),

                linkTo(methodOn(LocationController.class)
                        .closeUsers(user.getId(), 10)
                ).withRel(CLOSEUSERSREL),
                linkTo(methodOn(UserController.class).getUserInformation(user.getId())).withRel(GETUSERINFO)
        );
    }
}
