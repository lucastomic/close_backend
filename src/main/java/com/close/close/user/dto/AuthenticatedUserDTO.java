package com.close.close.user.dto;
import com.close.close.duck.Duck;
import com.close.close.duck.dto.DuckDTO;
import com.close.close.user.User;

import java.util.HashSet;
import java.util.Set;

public class AuthenticatedUserDTO extends UserDTO{
    private int ducksReceived;
    private Set<DuckDTO> ducksSent;
    public AuthenticatedUserDTO(User user) {
        super(user);
        this.ducksReceived = getNumberOfDucksReceived();
        this.ducksSent = calculateDucksSent();
    }

    public int getDucksReceived() {
        return ducksReceived;
    }

    public void setDucksReceived(int ducksReceived) {
        this.ducksReceived = ducksReceived;
    }

    public Set<DuckDTO> getDucksSent() {
        return ducksSent;
    }

    public void setDucksSent(Set<DuckDTO> ducksSent) {
        this.ducksSent = ducksSent;
    }

    private int getNumberOfDucksReceived(){
        Set<Duck> ducksReceived = (Set<Duck>) getPrivateField("ducksReceived");
        return ducksReceived.size();
    }

    private Set<DuckDTO> calculateDucksSent(){
        Set<Duck> ducksSent =  (Set<Duck>)getPrivateField("ducksSent");
        return getDucksParsedToDTO(ducksSent);
    }

    private Set<DuckDTO> getDucksParsedToDTO(Set<Duck> rawSet){
        Set<DuckDTO> response = new HashSet<>();
        for(Duck duck : rawSet){
            response.add(new DuckDTO(duck));
        }
        return response;
    }
}
