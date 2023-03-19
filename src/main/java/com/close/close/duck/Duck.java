/**
 * com.colose.colose.duck is the package which is in charge of managing the duck shipping logic.
 */
package com.close.close.duck;

import com.close.close.user.User;
import jakarta.persistence.*;

/**
 * Duck is the relation when a user sends a duck to another user.
 * This is a relation N-M (every user can send N ducks and receive M ducks).
 * A user only can send 1 duck to another user. So, there can't be duplicated rows.
 */
@Entity
public class Duck {
    /**
     * id is a composite key, stored in an embedded DuckId object.
     */
    @EmbeddedId
    private DuckId id;
    /**
     * sender is the relation to the user who has sent the duck
     */
    @ManyToOne
    @MapsId("senderId")
    private User sender;
    /**
     * receiver is the relation to the user who has received the duck
     */
    @ManyToOne
    @MapsId("receiverId")
    private User receiver;

    /**
     * Class constructor. Excpects the sender and receiver and stores them in the proper relations
     * and converts them in a composite key.
     * @param sender user who has sent the duck
     * @param receiver user who has received the duck
     */
    public Duck(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.id = new DuckId(sender.getId(),receiver.getId());
    }

    /**
     * Default constructor needed for JPA operations
     */
    public Duck(){

    }

    //Getters and setters
    public void setId(DuckId id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }
}

