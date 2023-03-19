package com.close.close.duck;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

/**
 * DuckId is a composite key embedded in the Duck entity.
 */
@Embeddable
public class DuckId implements Serializable {
    /**
     * receiverId is a number with the Id from the user who has received the duck
      */
    private Long receiverId;
    /**
     * senderId is a number with the Id from the user who has sent the duck
     */
    private Long senderId;

    /**
     * Class constructor. Expects the id from the users involved.
     * @param senderId Long with the Id from the user who has sent the duck
     * @param receiverId Long with the Id from the user who has received the duck
     */
    public DuckId(Long senderId,Long receiverId){
        super();
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    /**
     * Default constructor needed for JPA operations
     */
    public DuckId() {
    }

    //Getters and setters
    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }

}
