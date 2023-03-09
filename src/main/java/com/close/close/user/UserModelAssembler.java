package com.close.close.user;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;



/**
 * UserModelAssembler is in charge of modeling the User entity for APIRest responses.
 */
@Component
public class UserModelAssembler  implements RepresentationModelAssembler<User, EntityModel<User>> {
    //TODO:IMPLEMENT
    @Override
    public EntityModel<User> toModel(User user) {
        return EntityModel.of(user);
    }
}
