package com.close.close.location;

import com.close.close.location.space_partitioning.QueryResult;
import com.close.close.user.User;
import com.close.close.user.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationSenderService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final LocationService LOCATION_SERVICE;
    public static final String WS_MESSAGE_TRANSFER_DESTINATION = "/queue/closeusers";
    private final ArrayList<User> receivers ;
    private User currentReceiver;

    @Autowired
    LocationSenderService(SimpMessagingTemplate simpMessagingTemplate, LocationService locationService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        LOCATION_SERVICE = locationService;
        receivers = new ArrayList<>();
    }

    public void addReceiver(User user){
        receivers.add(user);
    }

    @Scheduled(fixedRate = 5000)
    public void sendMessages() {
        for (User user: receivers){
            currentReceiver = user;
            sendLocationToCurrentReceiver();
        }
    }

    private void sendLocationToCurrentReceiver(){
        Object responseBody = getResponseBody();
        String username = currentReceiver.getUsername();
        simpMessagingTemplate.convertAndSendToUser(username,WS_MESSAGE_TRANSFER_DESTINATION, responseBody);
    }

    private Object getResponseBody(){
        try {
            return getResponseBodyWhenLocationWasSentFirst();
        } catch (UserNotFoundException ex){
            return getNoLocationSentFirstResponseBody();
        }
    }

    private List<User> getResponseBodyWhenLocationWasSentFirst(){
        return LOCATION_SERVICE.closeUsers(currentReceiver);
    }

    private List<User>  getNoLocationSentFirstResponseBody(){
        return  new ArrayList<>();
    }
}
