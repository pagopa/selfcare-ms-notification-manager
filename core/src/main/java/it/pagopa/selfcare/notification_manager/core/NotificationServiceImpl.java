package it.pagopa.selfcare.notification_manager.core;

import it.pagopa.selfcare.commons.base.security.SelfCareUser;
import it.pagopa.selfcare.notification_manager.api.NotificationConnector;
import it.pagopa.selfcare.notification_manager.api.model.MailRequest;
import it.pagopa.selfcare.notification_manager.core.model.MessageRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class NotificationServiceImpl implements NotificationService {


    private final NotificationConnector notificationService;
    private final String customerCareMail;
    private final String noReplyMailAddress;
    private final String customerCareMailSubjectPrefix;


    @Autowired
    NotificationServiceImpl(NotificationConnector notificationService,
                            @Value("${notification_manager.mail.customer-care}") String customerCareMail,
                            @Value("${notification_manager.mail.no-reply}") String noReplyMailAddress,
                            @Value("${notification_manager.mail.customer-care-subject-prefix}") String customerCareMailSubjectPrefix) {
        this.notificationService = notificationService;
        this.customerCareMail = customerCareMail;
        this.noReplyMailAddress = noReplyMailAddress;
        this.customerCareMailSubjectPrefix = customerCareMailSubjectPrefix;
    }

    @SneakyThrows
    @Override
    public void sendMessage(MessageRequest messageRequest) {
        log.trace("NotificationServiceImpl.sendMessage start");
        log.debug("messageRequest = {}", messageRequest);
        Assert.notNull(messageRequest, "Message request must not be null");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Authentication is required");
        }
        if (!(authentication.getPrincipal() instanceof SelfCareUser)) {
            throw new IllegalStateException("Not SelfCareUSer principal");
        }
        SelfCareUser principal = ((SelfCareUser) authentication.getPrincipal());

        MailRequest mail = new MailRequest();
        mail.setFrom(noReplyMailAddress);
        mail.setTo(customerCareMail);
        mail.setSubject(customerCareMailSubjectPrefix + messageRequest.getSubject());
        mail.setContent(messageRequest.getContent());
        mail.setReplyTo(principal.getEmail());

        notificationService.sendMessage(mail);
        log.trace("NotificationServiceImpl.sendMessage end");

    }
}
