package io.github.waileong.fcm.service.model;

import io.github.waileong.fcm.service.domain.FcmError;
import io.github.waileong.fcm.service.domain.FcmMessage;

/**
 * Represents the response from an attempt to send a Firebase Cloud Messaging (FCM) notification.
 * This class encapsulates the outcome of the send operation, which includes details about the message
 * sent and any errors that occurred during the process.
 *
 * @author Wai Leong
 **/
public class FcmSendResponse {
    /**
     * The message part of the response, containing details about the FCM message sent.
     */
    private FcmMessage message;

    /**
     * The error part of the response, containing details about any errors that occurred during the send operation.
     */
    private FcmError error;

    /**
     * Gets the message details of the FCM send response.
     *
     * @return the FCM message details
     */
    public FcmMessage getMessage() {
        return message;
    }

    /**
     * Sets the message details of the FCM send response.
     *
     * @param message the FCM message details to set
     */
    public void setMessage(FcmMessage message) {
        this.message = message;
    }

    /**
     * Gets the error details of the FCM send response.
     *
     * @return the FCM error details
     */
    public FcmError getError() {
        return error;
    }

    /**
     * Sets the error details of the FCM send response.
     *
     * @param error the FCM error details to set
     */
    public void setError(FcmError error) {
        this.error = error;
    }

    /**
     * The builder class for {@link FcmSendResponse}. Provides a fluent API for constructing
     * an instance of {@link FcmSendResponse}.
     */
    public static final class Builder {
        private FcmMessage message;
        private FcmError error;

        private Builder() {
        }

        /**
         * Creates a new instance of the builder for {@link FcmSendResponse}.
         *
         * @return a new instance of {@link Builder}
         */
        public static Builder aFcmSendResponse() {
            return new Builder();
        }

        /**
         * Sets the message detail for the {@link FcmSendResponse} being built.
         *
         * @param message the FCM message detail
         * @return the builder instance
         */
        public Builder message(FcmMessage message) {
            this.message = message;
            return this;
        }

        /**
         * Sets the error detail for the {@link FcmSendResponse} being built.
         *
         * @param error the FCM error detail
         * @return the builder instance
         */
        public Builder error(FcmError error) {
            this.error = error;
            return this;
        }

        /**
         * Constructs the {@link FcmSendResponse} with the current builder settings.
         *
         * @return the newly constructed {@link FcmSendResponse}
         */
        public FcmSendResponse build() {
            FcmSendResponse fcmSendResponse = new FcmSendResponse();
            fcmSendResponse.setMessage(message);
            fcmSendResponse.setError(error);
            return fcmSendResponse;
        }
    }
}
