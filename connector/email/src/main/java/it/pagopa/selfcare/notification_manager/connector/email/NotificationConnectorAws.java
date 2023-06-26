package it.pagopa.selfcare.notification_manager.connector.email;

import it.pagopa.selfcare.notification_manager.api.NotificationConnector;
import it.pagopa.selfcare.notification_manager.api.exception.MailException;
import it.pagopa.selfcare.notification_manager.api.model.MailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Slf4j
@Service
@ConditionalOnProperty(
        value="notification.manager.connector.type",
        havingValue = "aws",
        matchIfMissing = false)
public class NotificationConnectorAws implements NotificationConnector {

    private final SesClient sesClient;
    public NotificationConnectorAws(SesClient sesClient) {
        this.sesClient = sesClient;
    }

    @Override
    public void sendMessage(MailRequest mailRequest) throws MailException {

        log.trace("sendMessage start");

        Assert.notNull(mailRequest, "the MailRequest must not be null");
        Assert.notNull(mailRequest.getTo(), "the destination must not be null");

        Destination destination = Destination.builder()
                .toAddresses(mailRequest.getTo())
                .build();

        Content content = Content.builder()
                .data(mailRequest.getContent())
                .build();

        Content sub = Content.builder()
                .data(mailRequest.getSubject())
                .build();

        Body body = Body.builder()
                .html(content)
                .build();

        Message msg = Message.builder()
                .subject(sub)
                .body(body)
                .build();

        SendEmailRequest emailRequest = SendEmailRequest.builder()
                .destination(destination)
                .message(msg)
                .source(mailRequest.getFrom())
                .build();

        try {
            log.trace("Attempting to send an email through Amazon SES using the AWS SDK for Java...");
            sesClient.sendEmail(emailRequest);
        } catch (SesException e) {
            log.error(e.getMessage());
            throw new MailException(e);
        }

        log.trace("sendMessage end");

    }
}

