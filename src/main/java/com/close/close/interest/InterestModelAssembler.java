package com.close.close.interest;

import com.close.close.user.User;
import com.close.close.user.UserController;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * InterestModelAssembler is in charge of modeling the User entity for APIRest responses.
 */
public class InterestModelAssembler implements RepresentationModelAssembler<Interest, EntityModel<Interest>> {

    @Override
    public EntityModel<Interest> toModel(Interest interest) {
        return EntityModel.of(
                interest,
                linkTo(methodOn(InterestController.class).getAll()).withRel("users"),
                linkTo(methodOn(InterestController.class).getOne(interest.getName())).withSelfRel()
        );
    }
}
