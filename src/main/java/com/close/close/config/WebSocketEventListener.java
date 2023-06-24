package com.close.close.config;

import com.close.close.location.LocationSenderService;
import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class WebSocketEventListener {
    private final LocationSenderService LOCATION_SENDER_SERVICE;
    private SessionSubscribeEvent currentEvent;
    @Autowired
    WebSocketEventListener(LocationSenderService locationSenderService){
        this.LOCATION_SENDER_SERVICE = locationSenderService;
    }
    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        currentEvent = event;
        if (isACloseUsersSubscription()) {
            User user = getUser();
            LOCATION_SENDER_SERVICE.addReceiver(user);
        }
    }

    private boolean isACloseUsersSubscription(){
        String simpDestination = getSimpDestiantion();
        return simpDestination.contains(LocationSenderService.WS_MESSAGE_TRANSFER_DESTINATION);
    }

    private User getUser(){
        var us = (UsernamePasswordAuthenticationToken) currentEvent.getUser();
        return (User)us.getPrincipal();
    }

    private String getSimpDestiantion( ){
        GenericMessage message = (GenericMessage) currentEvent.getMessage();
        return (String) message.getHeaders().get("simpDestination");
    }
}