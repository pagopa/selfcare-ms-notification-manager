package it.pagopa.selfcare.notification_manager.connector.email;

import it.pagopa.selfcare.notification_manager.api.NotificationConnector;
import it.pagopa.selfcare.notification_manager.api.exception.MailException;
import it.pagopa.selfcare.notification_manager.api.model.MailRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.mail.internet.MimeMessage;

@Slf4j
@Service
public class NotificationConnectorImpl implements NotificationConnector {

    private final JavaMailSender mailSender;


    @Autowired
    public NotificationConnectorImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }


    @Override
    public void sendMessage(MailRequest mailRequest) throws MailException {
        log.trace("sendMessage start");
        log.debug("sendMessage mailRequest = {}", mailRequest);
        Assert.notNull(mailRequest, "the MailRequest must not be null");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setFrom(mailRequest.getFrom());
            helper.setText(mailRequest.getContent());
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

