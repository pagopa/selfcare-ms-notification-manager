package it.pagopa.selfcare.notification_manager.web.handler;

import it.pagopa.selfcare.commons.web.model.Problem;
import it.pagopa.selfcare.notification_manager.core.exception.MessageRequestException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class NotificationExceptionsHandlerTest {

    private static final String DETAIL_MESSAGE = "detail message";

    private final NotificationExceptionsHandler handler;


    NotificationExceptionsHandlerTest() {
        this.handler = new NotificationExceptionsHandler();
    }


    @Test
    void handleFileValidationException() {
        // given
        MessageRequestException exceptionMock = Mockito.mock(MessageRequestException.class);
        Mockito.when(exceptionMock.getMessage())
                .thenReturn(DETAIL_MESSAGE);
        // when
        ResponseEntity<Problem> responseEntity = handler.handleFileValidationException(exceptionMock);
        // then
        assertNotNull(responseEntity);
        assertEquals(BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(DETAIL_MESSAGE, responseEntity.getBody().getDetail());
        assertEquals(BAD_REQUEST.value(), responseEntity.getBody().getStatus());
    }

}