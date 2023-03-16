package com.close.close.interest;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * InterestModelAssembler is in charge of modeling the User entity for APIRest responses.
 */
@Component
public class InterestModelAssembler implements RepresentationModelAssembler<Interest, EntityModel<Interest>> {

    /**
     * toModel takes an interest and parses it to a EntityModel, with a link to all the interests and a link
     * to the interest itself
     * @param interest interest to parse
     * @return entity model with the corresponding links
     */
    @Override
    public EntityModel<Interest> toModel(Interest interest) {
        return EntityModel.of(
                interest,
                linkTo(methodOn(InterestController.class).getAll()).withRel("interests"),
                linkTo(methodOn(InterestController.class).getOne(interest.getName())).withSelfRel()
        );
    }
}
