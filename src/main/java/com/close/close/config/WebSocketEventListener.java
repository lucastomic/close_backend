package com.close.close.config;

import com.close.close.location.LocationSenderService;
import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.AbstractSubProtocolEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class WebSocketEventListener {
    private final LocationSenderService LOCATION_SENDER_SERVICE;
    private AbstractSubProtocolEvent currentEvent;
    @Autowired
    WebSocketEventListener(LocationSenderService locationSenderService){
        this.LOCATION_SENDER_SERVICE = locationSenderService;
    }
    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
        currentEvent = event;
        if (isACloseUsersEvent()) {
            User user = getUser();
            LOCATION_SENDER_SERVICE.addReceiver(user);
        }
    }

    @EventListener
    public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {
        currentEvent = event;
        if (isACloseUsersEvent()) {
            User user = getUser();
            LOCATION_SENDER_SERVICE.removeReceiver(user);
        }
    }

    private boolean isACloseUsersEvent(){
        String simpDestination = getSimpDestiantion();
        return simpDestination.contains(LocationSenderService.WS_MESSAGE_TRANSFER_DESTINATION);
    }

    private User getUser(){
        var us = (UsernamePasswordAuthenticationToken) currentEvent.getUser();
        return (User)us.getPrincipal();
    }
    private String getSimpDestiantion(){
        if(currentEvent.getClass() == SessionSubscribeEvent.class){
            return getSimpDestiantionForSubscribeEvenets();
        }else{
            return getSimpDestiantionForUnubscribeEvenets();
        }
    }
    private String getSimpDestiantionForSubscribeEvenets(){
        GenericMessage message = (GenericMessage) currentEvent.getMessage();
        return (String) message.getHeaders().get("simpDestination");
    }
    private String getSimpDestiantionForUnubscribeEvenets(){
        GenericMessage message = (GenericMessage) currentEvent.getMessage();
        Map<String,Object> nativeHeaders = (Map<String,Object>) message.getHeaders().get("nativeHeaders");
        ArrayList<String> simpDestinations = (ArrayList<String>) nativeHeaders.get("simpDestination");
        return simpDestinations.get(0);
    }
}