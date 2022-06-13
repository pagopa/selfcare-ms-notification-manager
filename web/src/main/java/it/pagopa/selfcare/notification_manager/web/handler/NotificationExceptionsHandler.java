package it.pagopa.selfcare.notification_manager.web.handler;

import it.pagopa.selfcare.commons.web.model.Problem;
import it.pagopa.selfcare.commons.web.model.mapper.ProblemMapper;
import it.pagopa.selfcare.notification_manager.core.exception.MessageRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Exception handler for {@link it.pagopa.selfcare.notification_manager.web.controller.NotificationController}
 */
@Slf4j
@RestControllerAdvice
public class NotificationExceptionsHandler {

    @ExceptionHandler({MessageRequestException.class})
    ResponseEntity<Problem> handleFileValidationException(MessageRequestException e) {
        log.warn(e.toString());
        return ProblemMapper.toResponseEntity(new Problem(BAD_REQUEST, e.getMessage()));
    }

}