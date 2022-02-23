package it.pagopa.selfcare.notification_manager.core;

import it.pagopa.selfcare.notification_manager.core.model.MessageRequest;

public interface NotificationService {
    void sendMessageToCustomerCare(MessageRequest messageRequest);

    void sendMessageToUser(MessageRequest messageRequest);
}
