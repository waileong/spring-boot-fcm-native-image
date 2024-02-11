package io.github.waileong.fcm.service.impl;

import io.github.waileong.fcm.exception.FcmRestClientException;
import io.github.waileong.fcm.service.FcmNotificationService;
import io.github.waileong.fcm.service.FcmService;
import io.github.waileong.fcm.service.domain.*;
import io.github.waileong.fcm.service.model.FcmSendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Implements the {@link FcmNotificationService}, providing concrete methods to send
 * Firebase Cloud Messaging (FCM) notifications synchronously and asynchronously.
 * This implementation utilizes an {@link FcmService} to actually perform the sending of messages.
 *
 * @author Wai Leong
 */
@RegisterReflectionForBinding({FcmSendRequest.class, FcmMessage.class, FcmError.class, FcmErrorResponse.class})
public class FcmNotificationServiceImpl implements FcmNotificationService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final FcmService fcmService;

    @Autowired
    public FcmNotificationServiceImpl(FcmService fcmService) {
        this.fcmService = fcmService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FcmSendResponse sendSynchronousComplex(FcmSendRequest request) {
        return sendFcm(request);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<FcmSendResponse> sendSimpleNotification(String token, String title, String message, String collapseKey) {
        FcmMessage.Builder builder = getFcmMessageBuilder(token, title, message, collapseKey);
        return CompletableFuture.completedFuture(sendFcm(
                FcmSendRequest.Builder.aFcmSendRequest()
                        .message(builder.build())
                        .build()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<FcmSendResponse> sendSimpleNotificationWithData(String token, String title, String message, Map<String, String> data, String collapseKey) {
        FcmMessage.Builder builder = getFcmMessageBuilder(token, title, message, collapseKey);
        builder.data(data);
        return CompletableFuture.completedFuture(sendFcm(
                FcmSendRequest.Builder.aFcmSendRequest()
                        .message(builder.build())
                        .build()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompletableFuture<FcmSendResponse> sendSimpleData(String token, Map<String, String> data) {
        return CompletableFuture.completedFuture(sendFcm(
                FcmSendRequest.Builder.aFcmSendRequest()
                        .message(FcmMessage.Builder.aFcmMessage()
                                .token(token)
                                .data(data)
                                .build())
                        .build()));
    }

    private FcmMessage.Builder getFcmMessageBuilder(String token, String title, String message, String collapseKey) {
        FcmMessage.Builder builder = FcmMessage.Builder.aFcmMessage()
                .token(token)
                .notification(FcmNotification.Builder.aFcmNotification()
                        .title(title)
                        .body(message)
                        .build());

        if (!isBlank(collapseKey)) {
            Map<String, String> apnsHeaders = new HashMap<>();
            apnsHeaders.put("apns-collapse-id", collapseKey);
            builder.apns(FcmApns.Builder.aFcmApns()
                    .headers(apnsHeaders)
                    .build());
            builder.android(FcmAndroid.Builder.aFcmAndroid()
                    .collapseKey(collapseKey)
                    .notification(FcmAndroidNotification.Builder.aFcmAndroidNotification()
                            .tag(collapseKey)
                            .build())
                    .build());
        }
        return builder;
    }

    /**
     * Helper method to send FCM messages using the configured {@link FcmService}.
     * This method encapsulates the try-catch logic for {@link FcmRestClientException}
     * and constructs the appropriate {@link FcmSendResponse} based on the outcome.
     *
     * @param fcmSendRequest the request information for sending an FCM message
     * @return an {@link FcmSendResponse} indicating the result of the send operation
     */
    private FcmSendResponse sendFcm(FcmSendRequest fcmSendRequest) {
        try {
            FcmMessage fcmMessage = fcmService.send(fcmSendRequest);
            return FcmSendResponse.Builder.aFcmSendResponse()
                    .message(fcmMessage)
                    .build();
        } catch (FcmRestClientException e) {
            logger.warn("Failed to send FCM message", e);
            return FcmSendResponse.Builder.aFcmSendResponse()
                    .error(e.getError())
                    .build();
        }
    }
}
