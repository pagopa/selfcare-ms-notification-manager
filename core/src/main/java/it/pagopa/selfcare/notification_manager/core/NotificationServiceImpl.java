package it.pagopa.selfcare.notification_manager.core;

import it.pagopa.selfcare.commons.base.security.SelfCareUser;
import it.pagopa.selfcare.notification_manager.api.NotificationConnector;
import it.pagopa.selfcare.notification_manager.api.model.MailRequest;
import it.pagopa.selfcare.notification_manager.core.exception.MessageRequestException;
import it.pagopa.selfcare.notification_manager.core.model.MessageRequest;
import it.pagopa.selfcare.notification_manager.core.model.MultiReceiverMessageRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {


    private final NotificationConnector notificationService;
    private final String customerCareMail;
    private final String noReplyMailAddress;
    private final String customerCareMailSubjectPrefix;
    private final String userMailSubjectPrefix;


    @Autowired
    NotificationServiceImpl(NotificationConnector notificationService,
                            @Value("${notification_manager.mail.customer-care}") String customerCareMail,
                            @Value("${notification_manager.mail.no-reply}") String noReplyMailAddress,
                            @Value("${notification_manager.mail.customer-care-subject-prefix}") String customerCareMailSubjectPrefix,
                            @Value("${notification_manager.mail.user-subject-prefix}") String userMailSubjectPrefix
                            ) {
        this.notificationService = notificationService;
        this.customerCareMail = customerCareMail;
        this.noReplyMailAddress = noReplyMailAddress;
        this.customerCareMailSubjectPrefix = customerCareMailSubjectPrefix;
        this.userMailSubjectPrefix = userMailSubjectPrefix;
    }

    @Deprecated
    @SneakyThrows
    @Override
    public void sendMessageToCustomerCare(MessageRequest messageRequest) {
        log.trace("sendMessageToCustomerCare start");
        log.debug("sendMessageToCustomerCare messageRequest = {}", messageRequest);
        Assert.notNull(messageRequest, "request must not be null");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Assert.state(authentication != null, "Authentication is required");
        Assert.state(authentication.getPrincipal() instanceof SelfCareUser, "Not SelfCareUSer principal");
        SelfCareUser principal = ((SelfCareUser) authentication.getPrincipal());

        MailRequest mail = new MailRequest();
        mail.setFrom(noReplyMailAddress);
        mail.setTo(customerCareMail);
        mail.setSubject(String.format("%s%s %s", customerCareMailSubjectPrefix, messageRequest.getSubject(), principal.getFiscalCode()));
        mail.setContent(messageRequest.getContent());
        if (principal.getEmail() == null) {
            if (messageRequest.getSenderEmail() != null) {
                mail.setReplyTo(Optional.of(messageRequest.getSenderEmail()));
            } else {
                throw new MessageRequestException("Missing replyTo address");
            }
        } else {
            mail.setReplyTo(Optional.of(principal.getEmail()));
        }

        notificationService.sendMessage(mail);
        log.trace("sendMessageToCustomerCare end");

    }

    @SneakyThrows
    @Override
    public void sendMessageToUser(MessageRequest messageRequest) {
        log.trace("sendMessageToUser start");
        log.debug("sendMessageToUser messageRequest = {}", messageRequest);
        Assert.notNull(messageRequest, "Message request must not be null");

        MailRequest mail = constructMailRequest(messageRequest.getReceiverEmail(), userMailSubjectPrefix + messageRequest.getSubject(), messageRequest.getContent());
        notificationService.sendMessage(mail);
        log.trace("sendMessageToUser end");
    }

    @Override
    public void sendMessageToUsers(MultiReceiverMessageRequest messageRequest) {
        log.trace("sendMessageToUsers start");
        log.debug("sendMessageToUsers messageRequest = {}", messageRequest);
        Assert.notNull(messageRequest, "Multi receiver Message request must not be null");

        messageRequest.getReceiverEmail().stream()
                .map(s -> constructMailRequest(s, messageRequest.getSubject(), messageRequest.getContent()))
                .forEach(this::sendMessage);

        log.trace("sendMessageToUser end");
    }

    @SneakyThrows
    private void sendMessage(MailRequest mailRequest) {
        notificationService.sendMessage(mailRequest);
    }

    private MailRequest constructMailRequest(String to, String subject, String content) {
        MailRequest mail = new MailRequest();
        mail.setFrom(noReplyMailAddress);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setContent(content);
        mail.setReplyTo(Optional.empty());
        return mail;
    }
}
