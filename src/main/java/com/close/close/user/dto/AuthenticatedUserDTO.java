package com.close.close.user.dto;
import com.close.close.duck.Duck;
import com.close.close.user.User;
import java.util.Set;

public class AuthenticatedUserDTO extends UserDTO{
    private int ducksReceived;
    private Set<Duck> ducksSent;
    public AuthenticatedUserDTO(User user) {
        super(user);
        this.ducksReceived = calculateNumberOfDucksReceived();
        this.ducksSent = (Set<Duck>)getPrivateField("ducksSent");
    }

    public int getDucksReceived() {
        return ducksReceived;
    }

    public void setDucksReceived(int ducksReceived) {
        this.ducksReceived = ducksReceived;
    }

    public Set<Duck> getDucksSent() {
        return ducksSent;
    }

    public void setDucksSent(Set<Duck> ducksSent) {
        this.ducksSent = ducksSent;
    }

    private int calculateNumberOfDucksReceived(){
        Set<Duck> ducksReceived = (Set<Duck>) getPrivateField("ducksReceived");
        return ducksReceived.size();
    }

}
