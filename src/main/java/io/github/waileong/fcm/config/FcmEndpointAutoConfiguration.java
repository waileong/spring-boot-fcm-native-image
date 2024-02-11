package io.github.waileong.fcm.config;

import io.github.waileong.fcm.aop.FcmNotificationServiceAdvice;
import io.github.waileong.fcm.controller.FcmController;
import io.github.waileong.fcm.service.FcmService;
import io.github.waileong.fcm.service.impl.FcmNotificationServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.util.concurrent.Executor;

import static org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration.APPLICATION_TASK_EXECUTOR_BEAN_NAME;

/**
 * Auto-configuration class for FCM endpoint components. This class is responsible for
 * setting up the necessary beans related to FCM within the Spring Boot application context.
 * It uses Spring Boot's auto-configuration capabilities to simplify the integration and configuration
 * of FCM services and aspects, ensuring that they are properly configured and ready to use.
 *
 * The configuration is designed to have the highest precedence to ensure that it is applied
 * before any other auto-configuration related to FCM, allowing for overriding or customizing
 * the setup as needed by the application.
 *
 * This class explicitly imports {@link FcmController} to ensure it's registered within the application
 * context and declares beans for {@link FcmNotificationServiceImpl} and {@link FcmNotificationServiceAdvice}
 * to provide services and advice for handling FCM notifications.
 *
 * @author Wai Leong
 */
@AutoConfiguration(before = FcmAutoConfiguration.class)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@Import({FcmController.class})
public class FcmEndpointAutoConfiguration {

    /**
     * Declares the bean for {@link FcmNotificationServiceImpl}. This service implementation
     * is responsible for handling the business logic associated with sending FCM notifications.
     *
     * @param fcmService the FcmService instance to be used by the FcmNotificationServiceImpl
     * @return an instance of {@link FcmNotificationServiceImpl}
     */
    @Bean
    public FcmNotificationServiceImpl fcmNotificationService(FcmService fcmService) {
        return new FcmNotificationServiceImpl(fcmService);
    }

    /**
     * Declares the bean for {@link FcmNotificationServiceAdvice}. This advice is applied
     * to FCM notification services to provide additional functionalities such as auditing.
     *
     * @param executor the executor to be used for asynchronous operations within the advice,
     *                 qualified by {@code APPLICATION_TASK_EXECUTOR_BEAN_NAME}
     * @return an instance of {@link FcmNotificationServiceAdvice}
     */
    @Bean
    public FcmNotificationServiceAdvice fcmNotificationServiceAdvice(
            @Qualifier(APPLICATION_TASK_EXECUTOR_BEAN_NAME) Executor executor) {
        return new FcmNotificationServiceAdvice(executor);
    }
}
