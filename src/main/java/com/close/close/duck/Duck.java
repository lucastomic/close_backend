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
    @EmbeddedId
    private DuckId id;

    @ManyToOne
    @MapsId("senderId")
    private User sender;
    @ManyToOne
    @MapsId("receiverId")
    private User receiver;

    public Duck(User sender, User receiver) {
        if (sender.equals(receiver)) {
            throw new DuckToItselfException(sender.getId());
        }
        this.sender = sender;
        this.receiver = receiver;
        this.id = new DuckId(sender.getId(), receiver.getId());
    }

    public Duck() {

    }

}

