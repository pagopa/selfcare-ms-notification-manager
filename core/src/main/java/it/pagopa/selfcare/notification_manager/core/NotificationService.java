package it.pagopa.selfcare.notification_manager.core;

import it.pagopa.selfcare.notification_manager.core.exception.MessageRequestException;
import it.pagopa.selfcare.notification_manager.core.model.MessageRequest;

public interface NotificationService {
    void sendMessage(MessageRequest messageRequest) throws MessageRequestException;
}
