package io.github.waileong.fcm.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for a Spring Boot application focused on FCM endpoints.
 * It sets up and starts the application, enabling Spring Boot's features such as auto-configuration
 * and component scanning. Simply run this class's main method to start the application.
 *
 * @author Wai Leong
 */
@SpringBootApplication
public class FcmEndpointBootConfiguration {

    /**
     * Starts the Spring Boot application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SpringApplication.run(FcmEndpointBootConfiguration.class, args);
    }
}
