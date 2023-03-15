package com.close.close.apirest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.URI;

/**
 * Rest saver takes an entity and saves it on the database
 * following the API REST standard (hiperlinks, headers, etc)
 * @param <T> Entity to be saved
 */
public class RestSaver<T>{
    /**
     * repository is the user's repository for DB interaction
     */
    private final JpaRepository<T,?> repository;
    /**
     * assembler is the user's model assembler for converting User models to
     * AIPRest responses
     */
    private final RepresentationModelAssembler<T,EntityModel<T>> assembler;;

    /**
     * Class constructor. It implements dependency injection.
     * @param repository user's repository
     * @param assembler user's model assembler
     */
    public RestSaver(JpaRepository<T,?> repository, RepresentationModelAssembler<T,EntityModel<T>> assembler){
        this.repository = repository;
        this.assembler = assembler;
    };

    /**
     * saveEntity saves an object of type T on the database. The object information is passed in the request's body.
     * If the user is saved successfully it returns an 200 OK status and a response modeled by the assembler.
     * @return ResponseEntity with the link to the new object inserted in the
     * location header and the object's information in the request's body
     */
    public ResponseEntity<?> saveEntity(@RequestBody T newUser){
        EntityModel<T> entityModel =  assembler.toModel(repository.save(newUser));
        URI location = this.parseEntityModelToLink(entityModel);
        return ResponseEntity.created(location).body(entityModel);
    }

    /**
     * parseEntityModelToLink parses an entity model into a URI to access to itself inside the API
     * @param entityModel entity model to parse
     * @return URI object with the respective direction
     */
    private URI parseEntityModelToLink(EntityModel<?> entityModel){
        return entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri();
    }
}

