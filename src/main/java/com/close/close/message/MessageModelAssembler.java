package com.close.close.message;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class MessageModelAssembler implements RepresentationModelAssembler<Message, EntityModel<Message>> {

    private final String GETMESSAGEREL ="getMessage";

    @Override
    public EntityModel<Message> toModel(Message message) {
        return EntityModel.of(
            message,
            linkTo(methodOn(MessageController.class).getMessage(1L)
            ).withRel(GETMESSAGEREL)
        );
    }

    @Override
    public CollectionModel<EntityModel<Message>> toCollectionModel(Iterable<? extends Message> entities) {
        return RepresentationModelAssembler.super.toCollectionModel(entities);
    }
}
