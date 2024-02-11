package io.github.waileong.fcm.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

/**
 * Represents a request for sending a simple notification using Firebase Cloud Messaging (FCM).
 * This class encapsulates the necessary information required for sending a notification, including the receiver's token,
 * the notification title, message, and any additional data to accompany the notification.
 *
 * @author Wai Leong
 */
public class SimpleNotificationWithDataRequest {

    /**
     * The token of the device to which the notification will be sent.
     * This field must not be blank.
     */
    @NotBlank
    private String token;

    /**
     * The title of the notification.
     * This field must not be blank.
     */
    @NotBlank
    private String title;

    /**
     * The message body of the notification.
     * This field must not be blank.
     */
    @NotBlank
    private String message;
    /**
     * The collapse key of the notification.
     */
    private String collapseKey;

    /**
     * Additional data to send along with the notification.
     */
    @NotNull
    @NotEmpty
    private Map<String, String> data;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCollapseKey() {
        return collapseKey;
    }

    public void setCollapseKey(String collapseKey) {
        this.collapseKey = collapseKey;
    }

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
