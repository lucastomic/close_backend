package com.close.close.user;

import com.close.close.duck.DuckController;
import com.close.close.interest.InterestController;
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
    private final String SENDDUCKREL = "sendDuck";
    private final String DELETEUSER = "deleteUser";
    private final String DUCKSRECEIVEDREL = "ducksReceived";
    private final String REMOVE_DUCK = "removeDuck";
    private final String SENDLOCATIONREL = "sendLocation";
    private final String CLOSEUSERSREL = "closeUsers";
    private final String ADDINTEREST = "addInterest";
    private final String REMOVE_INTEREST = "removeInterest";
    private final String GETUSERINFO ="userInfo";
    private final String GETDUCKSSENT ="getDucksSent";



    @Override
    public @NotNull EntityModel<User> toModel(@NotNull User user) {
        return EntityModel.of(
                user,
                //User Controller Links
                linkTo(methodOn(UserController.class)
                        .addInterest("Chess")
                ).withRel(ADDINTEREST),
                linkTo(methodOn(UserController.class)
                        .delete()
                ).withRel(DELETEUSER),
                linkTo(methodOn(UserController.class)
                        .removeInterest("Chess")
                ).withRel(REMOVE_INTEREST),
                //Duck Controller Links
                linkTo(methodOn(DuckController.class)
                        .sendDuck(user.getId())
                ).withRel(SENDDUCKREL),

                linkTo(methodOn(DuckController.class)
                        .getDucksReceived()
                ).withRel(DUCKSRECEIVEDREL),

                linkTo(methodOn(DuckController.class)
                        .getDucksSent()
                ).withRel(GETDUCKSSENT),

                linkTo(methodOn(DuckController.class)
                        .getDucksSent()
                ).withRel(REMOVE_DUCK),

                //Location Controller Links
                linkTo(methodOn(LocationController.class)
                        .sendLocation( new Location(0, 0))
                ).withRel(SENDLOCATIONREL),

                linkTo(methodOn(LocationController.class)
                        .closeUsers(10)
                ).withRel(CLOSEUSERSREL),
                linkTo(methodOn(UserController.class).getUserInformation()).withRel(GETUSERINFO)

        );
    }
}
