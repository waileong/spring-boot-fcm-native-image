package io.github.waileong.fcm.service;

import io.github.waileong.fcm.service.domain.FcmSendRequest;
import io.github.waileong.fcm.service.model.FcmSendResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Defines the contract for sending Firebase Cloud Messaging (FCM) notifications.
 * This service provides methods for sending various types of FCM notifications,
 * including simple notifications, notifications with data, and data-only messages.
 *
 * @author Wai Leong
 **/
@Validated
public interface FcmNotificationService {

    /**
     * Sends a synchronous complex FCM notification based on the provided request.
     *
     * @param request the FCM send request containing all necessary information for sending a notification
     * @return an instance of FcmSendResponse indicating the result of the send operation
     */
    FcmSendResponse sendSynchronousComplex(@Valid @NotNull FcmSendRequest request);

    /**
     * Asynchronously sends a simple FCM notification consisting of a token, title, and message.
     *
     * @param token       the target device token to which the notification will be sent
     * @param title       the title of the notification
     * @param message     the message body of the notification
     * @param collapseKey the collapse key of the notification
     * @return a CompletableFuture that will complete with the result of the send operation
     */
    CompletableFuture<FcmSendResponse> sendSimpleNotification(
            @NotBlank String token, @NotBlank String title, @NotBlank String message, String collapseKey);

    /**
     * Asynchronously sends a simple FCM notification with additional data. The notification
     * includes a token, title, message, and a map of data to be sent along with the notification.
     *
     * @param token       the target device token to which the notification will be sent
     * @param title       the title of the notification
     * @param message     the message body of the notification
     * @param data        a map of key-value pairs representing the data to be sent with the notification
     * @param collapseKey the collapse key of the notification
     * @return a CompletableFuture that will complete with the result of the send operation
     */
    CompletableFuture<FcmSendResponse> sendSimpleNotificationWithData(
            @NotBlank String token, @NotBlank String title, @NotBlank String message,
            @NotNull @NotEmpty Map<String, String> data, String collapseKey);

    /**
     * Asynchronously sends a data-only FCM message. This type of message contains no notification
     * payload and is intended for data transmission to the target device.
     *
     * @param token the target device token to which the data message will be sent
     * @param data  a map of key-value pairs representing the data to be sent
     * @return a CompletableFuture that will complete with the result of the send operation
     */
    CompletableFuture<FcmSendResponse> sendSimpleData(
            @NotBlank String token, @NotNull @NotEmpty Map<String, String> data);

}
