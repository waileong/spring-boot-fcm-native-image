package io.github.waileong.fcm.controller.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Map;

/**
 * Defines a request structure for transmitting data payloads using Firebase Cloud Messaging (FCM).
 * Rather than focusing on notification content (such as titles and messages), this class is geared towards sending
 * arbitrary data payloads to a specified device. This capability is useful for sending silent data updates or commands
 * that can be processed by the receiving application in the background.
 * <p>
 * The class requires a device token to identify the target device and a map of data that constitutes the payload.
 * Each entry in the data map represents a key-value pair that the application can use for various purposes.
 * The data map must not be null or empty, ensuring that every request carries meaningful data.
 *
 * @author Wai Leong
 */
public class SimpleDataRequest {

    /**
     * Token identifying the target device for the data payload. This token is typically obtained from the
     * Firebase SDK on the client device. It is essential for routing the data payload to the correct device.
     * This field is mandatory and must not be blank.
     */
    @NotBlank
    private String token;

    /**
     * A map containing the data payload to be sent. Each key-value pair in the map represents a piece of data
     * that the application on the receiving device can use. This could include configuration settings, commands,
     * or any other data the application needs to function or update its state.
     * <p>
     * The map must not be null or empty, ensuring that the request always carries data with it.
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

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }
}
