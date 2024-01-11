package it.pagopa.selfcare.notification_manager.core;

import it.pagopa.selfcare.notification_manager.core.model.MessageRequest;
import it.pagopa.selfcare.notification_manager.core.model.MultiReceiverMessageRequest;

public interface NotificationService {
    @Deprecated
    void sendMessageToCustomerCare(MessageRequest messageRequest);

    void sendMessageToUser(MessageRequest messageRequest);

    void sendMessageToUsers(MultiReceiverMessageRequest messageRequest);
}
