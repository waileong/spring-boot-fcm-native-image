package io.github.waileong.fcm.aop;

import io.github.waileong.fcm.exception.FcmRestClientException;
import io.github.waileong.fcm.service.domain.FcmError;
import io.github.waileong.fcm.service.model.FcmSendResponse;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Aspect for auditing operations of the FcmNotificationService.
 * It logs the execution time, method name, arguments, and result of each method invoked in the service.
 * This aspect specifically targets all methods within the FcmNotificationService and logs important
 * execution details asynchronously, utilizing a provided Executor for managing asynchronous logging tasks.
 *
 * @author Wai Leong
 */
@Aspect
public class FcmNotificationServiceAdvice {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.S");
    private final Logger auditLogger = LoggerFactory.getLogger("fcm.notification.audit");
    private final Executor executor;

    /**
     * Constructs a FcmNotificationServiceAdvice with a specific executor for asynchronous operations.
     *
     * @param executor the Executor used for handling asynchronous logging tasks
     */
    public FcmNotificationServiceAdvice(Executor executor) {
        this.executor = executor;
    }

    /**
     * Around advice that captures and logs execution details of FcmNotificationService methods.
     * This includes logging the method name, execution time, target recipients, notification title,
     * and any response message or errors. For asynchronous methods returning CompletableFuture,
     * logging is performed upon future completion.
     *
     * @param pjp the proceeding join point representing the intercepted method invocation
     * @return the result of the method invocation
     * @throws Throwable to rethrow any exceptions thrown by the target method
     */
    @Around("execution(* io.github.waileong.fcm.service.FcmNotificationService.*(..))")
    public Object aroundSendNotificationService(ProceedingJoinPoint pjp) throws Throwable {
        final ZonedDateTime startTime = ZonedDateTime.now();
        final Object[] args = pjp.getArgs();
        final String method = pjp.getSignature().getName();
        try {
            Object result = pjp.proceed();
            if (result instanceof CompletableFuture<?> completableFuture) {
                completableFuture.whenCompleteAsync((o, throwable) ->
                        doAuditLog(o, startTime, method, args), this.executor);
            } else {
                doAuditLog(result, startTime, method, args);
            }
            return result;
        } catch (FcmRestClientException ex) {
            doAuditLog(ex.getError(), startTime, method, args);
            throw ex;
        }
    }

    /**
     * Performs the actual logging of the method execution details.
     * This method constructs and logs a message containing the start time, execution duration,
     * method name, recipient token (if applicable), notification title (for simple notifications),
     * and any response message or error details.
     *
     * @param o         the result object from the method invocation, used to extract response details
     * @param startTime the start time of the method execution
     * @param method    the name of the invoked method
     * @param args      the arguments passed to the method
     */
    private void doAuditLog(Object o, ZonedDateTime startTime, String method, Object[] args) {
        long elapsedTime = Duration.between(startTime, ZonedDateTime.now()).toMillis();
        String respMsg = "";
        if (o != null) {
            switch (o) {
                case FcmSendResponse response when response.getError() != null ->
                        respMsg = String.valueOf(response.getError());
                case FcmError error -> respMsg = String.valueOf(error);
                default -> respMsg = String.valueOf(o);

            }
        }

        String sentTo = "";
        if (args[0] instanceof String str) {
            sentTo = str;
        }

        String title = "";
        if (method.startsWith("sendSimpleNotification")) {
            title = String.valueOf(args[1]);
        }

        if (auditLogger.isInfoEnabled()) {
            auditLogger.info("{},{},{},{},{},{}",
                    DATE_FORMAT.format(startTime),
                    elapsedTime,
                    method,
                    sentTo,
                    title,
                    respMsg
            );
        }
    }
}
