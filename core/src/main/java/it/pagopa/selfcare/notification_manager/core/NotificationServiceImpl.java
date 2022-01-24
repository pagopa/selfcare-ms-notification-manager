package it.pagopa.selfcare.notification_manager.core;

import it.pagopa.selfcare.notification_manager.api.NotificationConnector;
import org.springframework.beans.factory.annotation.Autowired;

public class NotificationServiceImpl implements NotificationService {

    private final NotificationConnector notificationService;

    @Autowired
    public NotificationServiceImpl(NotificationConnector notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void sendMessage(String content) {
        //TODO get logged user info and set the content from the String content

    }
}
