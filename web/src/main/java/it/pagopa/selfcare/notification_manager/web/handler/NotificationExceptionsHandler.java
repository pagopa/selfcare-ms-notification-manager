package it.pagopa.selfcare.notification_manager.web.handler;

import it.pagopa.selfcare.commons.web.model.ErrorResource;
import it.pagopa.selfcare.notification_manager.core.exception.MessageRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Exception handler for {@link it.pagopa.selfcare.notification_manager.web.controller.NotificationController}
 */
@Slf4j
@RestControllerAdvice
public class NotificationExceptionsHandler {

    @ExceptionHandler({MessageRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ErrorResource handleFileValidationException(MessageRequestException e) {
        log.warn(e.getMessage());
        return new ErrorResource(e.getMessage());
    }

}