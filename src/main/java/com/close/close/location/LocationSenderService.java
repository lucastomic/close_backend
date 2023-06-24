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

@Service
public class LocationSenderService {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final LocationService LOCATION_SERVICE;
    public static final String WS_MESSAGE_TRANSFER_DESTINATION = "/queue/closeusers";
    private final double RADIUS = 10;
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

    private ResponseEntity<?> getResponseBody(){
        try {
            return getResponseBodyWhenLocationWasSentFirst();
        } catch (UserNotFoundException ex){
            return getNoLocationSentFirstResponseBody();
        }
    }

    private ResponseEntity<QueryResult<UserAndLocation>> getResponseBodyWhenLocationWasSentFirst(){
        Long userId = currentReceiver.getId();
        QueryResult<UserAndLocation> result = LOCATION_SERVICE.closeUsers(userId, RADIUS);
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<String> getNoLocationSentFirstResponseBody(){
        return ResponseEntity.status(400).body("Tried to retrieve close users when no location was sent first");
    }
}
