package io.github.waileong.fcm.controller;

import io.github.waileong.fcm.controller.model.SimpleDataRequest;
import io.github.waileong.fcm.controller.model.SimpleNotificationRequest;
import io.github.waileong.fcm.controller.model.SimpleNotificationWithCollapseRequest;
import io.github.waileong.fcm.controller.model.SimpleNotificationWithDataRequest;
import io.github.waileong.fcm.service.FcmNotificationService;
import io.github.waileong.fcm.service.domain.FcmSendRequest;
import io.github.waileong.fcm.service.model.FcmSendResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for handling Firebase Cloud Messaging (FCM) notification requests.
 * Provides endpoints for sending synchronous complex notifications, simple notifications,
 * simple notifications with additional data, and data-only messages.
 *
 * @author Wai Leong
 */
@Tag(name = "FCM", description = "FCM Notification Api")
@RestController
public class FcmController {
    private final FcmNotificationService fcmNotificationService;

    /**
     * Constructs an FcmController with the specified FcmNotificationService.
     *
     * @param fcmNotificationService the service used for sending FCM notifications
     */
    @Autowired
    public FcmController(FcmNotificationService fcmNotificationService) {
        this.fcmNotificationService = fcmNotificationService;
    }

    /**
     * Endpoint to send a synchronous complex FCM notification.
     *
     * @param fcmRequest the FCM send request containing all necessary information for the notification
     * @return the response from the FCM notification sends operation
     */
    @Operation(summary = "Endpoint to send a synchronous complex FCM notification.")
    @PostMapping(value = "/synchronousComplex",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public FcmSendResponse sendSynchronousComplex(@Valid @RequestBody FcmSendRequest fcmRequest) {
        return this.fcmNotificationService.sendSynchronousComplex(fcmRequest);
    }

    /**
     * Endpoint to send a simple FCM notification.
     *
     * @param request the request containing the token, title, and message for the notification
     */
    @Operation(summary = "Endpoint to send a simple FCM notification.")
    @PostMapping(value = "/simple",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void sendSimpleNotification(@Valid @RequestBody SimpleNotificationRequest request) {
        this.fcmNotificationService.sendSimpleNotification(
                request.getToken(), request.getTitle(), request.getMessage(), null);
    }

    /**
     * Endpoint to send a simple FCM notification with Collapse Key.
     *
     * @param request the request containing the token, title, and message for the notification
     */
    @Operation(summary = "Endpoint to send a simple FCM notification with Collapse Key.")
    @PostMapping(value = "/simpleWithCollapse",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void sendSimpleNotificationWithCollapse(@Valid @RequestBody SimpleNotificationWithCollapseRequest request) {
        this.fcmNotificationService.sendSimpleNotification(
                request.getToken(), request.getTitle(), request.getMessage(), request.getCollapseKey());
    }


    /**
     * Endpoint to send a simple FCM notification with additional data.
     *
     * @param request the request containing the token, title, message, and data for the notification
     */
    @Operation(summary = "Endpoint to send a simple FCM notification with additional data.")
    @PostMapping(value = "/simpleWithData",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void sendSimpleNotificationWithData(@Valid @RequestBody SimpleNotificationWithDataRequest request) {
        this.fcmNotificationService.sendSimpleNotificationWithData(
                request.getToken(), request.getTitle(), request.getMessage(), request.getData(), request.getCollapseKey());
    }

    /**
     * Endpoint to send a data-only FCM message.
     *
     * @param request the request containing the token and data for the message
     */
    @Operation(summary = "Endpoint to send a data-only FCM message.")
    @PostMapping(value = "/data",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public void sendSimpleData(@Valid @RequestBody SimpleDataRequest request) {
        this.fcmNotificationService.sendSimpleData(request.getToken(), request.getData());
    }

}
