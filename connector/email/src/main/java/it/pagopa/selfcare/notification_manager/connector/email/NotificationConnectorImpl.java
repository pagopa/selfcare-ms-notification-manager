package it.pagopa.selfcare.notification_manager.connector.email;

import it.pagopa.selfcare.commons.base.logging.LogUtils;
import it.pagopa.selfcare.notification_manager.api.NotificationConnector;
import it.pagopa.selfcare.notification_manager.api.exception.MailException;
import it.pagopa.selfcare.notification_manager.api.model.MailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
@ConditionalOnProperty(
        value="spring.mail.connector.type",
        havingValue = "smtp")
public class NotificationConnectorImpl implements NotificationConnector {

    private final JavaMailSender mailSender;


    @Autowired
    public NotificationConnectorImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void sendMessage(MailRequest mailRequest) throws MailException {
        log.trace("sendMessage start");
        log.debug(LogUtils.CONFIDENTIAL_MARKER, "sendMessage mailRequest = {}", mailRequest);
        Assert.notNull(mailRequest, "the MailRequest must not be null");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(mailRequest.getFrom());
            helper.setText(mailRequest.getContent(), true);
            helper.setTo(mailRequest.getTo());
            helper.setSubject(mailRequest.getSubject());
            if (mailRequest.getReplyTo().isPresent()) {
                helper.setReplyTo(mailRequest.getReplyTo().get());
            }
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new MailException(e);
        }
        log.trace("sendMessage end");


    }
}

