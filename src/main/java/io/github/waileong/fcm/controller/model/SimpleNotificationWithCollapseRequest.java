package io.github.waileong.fcm.controller.model;

import jakarta.validation.constraints.NotBlank;

/**
 * Represents a request for sending a simple notification using Firebase Cloud Messaging (FCM).
 * This class encapsulates the necessary information required for sending a notification, including the receiver's token,
 * the notification title, and message to accompany the notification.
 *
 * @author Wai Leong
 */
public class SimpleNotificationWithCollapseRequest {

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
    @NotBlank
    private String collapseKey;

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
}
