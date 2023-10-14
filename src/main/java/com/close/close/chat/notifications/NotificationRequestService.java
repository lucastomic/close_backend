package com.close.close.chat.notifications;

import com.close.close.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

@Service
class NotificationRequestService {
    private User sender;
    private User receiver;
    final private String notificationEndpointWithoutProjectName = "https://fcm.googleapis.com/v1/projects/%s/messages:send";
    @Value("${firebase.project}")
    private String projectName;
    @Value("${firebase.token}")
    private String accessToken;
    private HttpURLConnection connection;
    private JsonNotificationFormatter jsonNotificationFormatter;

    public NotificationRequestService(JsonNotificationFormatter jsonNotificationFormatter) {
        this.jsonNotificationFormatter = jsonNotificationFormatter;
    }

    public int send(User sender, User receiver) throws IOException {
        this.sender = sender;
        this.receiver = receiver;
        initializeConnection();
        setRequestConfig();
        setRequestBody();
        return connection.getResponseCode();
    }

    private void initializeConnection() throws IOException {
        URL url = new URL(getNotificationEndpoint());
        connection = (HttpURLConnection) url.openConnection();
    }
    private void setRequestConfig() throws ProtocolException {
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + accessToken);
    }

    private void setRequestBody() throws IOException {
        String jsonInputString = jsonNotificationFormatter.getJson(sender, receiver);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(jsonInputString);
        wr.flush();
        wr.close();
    }

    private String getNotificationEndpoint(){
        return notificationEndpointWithoutProjectName.formatted(projectName);
    }
}
