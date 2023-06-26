package it.pagopa.selfcare.notification_manager.connector.email;

import it.pagopa.selfcare.notification_manager.api.exception.MailException;
import it.pagopa.selfcare.notification_manager.api.model.MailRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.SendEmailRequest;
import software.amazon.awssdk.services.ses.model.SendEmailResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
class NotificationConnectorAwsTest {
    private static final String FROM = "noreply@pagopa.it";
    private static final String TO = "selfcare_ssistenza.pagopa.com";
    private static final String MESSAGE = "test message";
    private static final String SUBJECT = " test";
    private static final String RECEIVER = "user@mail.com";

    @InjectMocks
    private NotificationConnectorAws notificationService;

    @Mock
    private SesClient sesClient;



    @Test
    void sendEmail_WithCorrectPayload() throws Exception {
        //given
        MailRequest mail = new MailRequest();
        mail.setTo(TO);
        mail.setFrom(FROM);
        mail.setContent(MESSAGE);
        mail.setSubject(SUBJECT);
        mail.setReplyTo(Optional.of("test@example.com"));
        SendEmailResponse response = mock(SendEmailResponse.class);
        when(sesClient.sendEmail(ArgumentMatchers.any(SendEmailRequest.class))).thenReturn(response);
        //when
        notificationService.sendMessage(mail);
        //then

    }


    @Test
    void sendEmail_WithException() {
        //given
        MailRequest mail = new MailRequest();
        mail.setTo(null);
        mail.setFrom(null);
        mail.setContent(MESSAGE);
        mail.setSubject(SUBJECT);
        mail.setReplyTo(Optional.of("test@example.com"));

        SendEmailResponse response = mock(SendEmailResponse.class);
        when(sesClient.sendEmail(ArgumentMatchers.any(SendEmailRequest.class))).thenReturn(response);
        //when
        Executable executable = () -> notificationService.sendMessage(mail);
        //then
        assertThrows(IllegalArgumentException.class, executable);


    }

    @Test
    void sendEmail_emptyReplyTo() throws MailException {
        //given
        MailRequest mail = new MailRequest();
        mail.setTo(RECEIVER);
        mail.setFrom(FROM);
        mail.setContent(MESSAGE);
        mail.setSubject(SUBJECT);
        SendEmailResponse response = mock(SendEmailResponse.class);
        when(sesClient.sendEmail(ArgumentMatchers.any(SendEmailRequest.class))).thenReturn(response);
        //when
        notificationService.sendMessage(mail);
        //then
    }

    @Test
    void sendEmail_nullRequestMail() {
        IllegalArgumentException assertThrows = assertThrows(IllegalArgumentException.class, () -> notificationService.sendMessage(null));
        assertEquals("the MailRequest must not be null", assertThrows.getMessage());
    }
}
