package it.pagopa.selfcare.notification_manager.api;


import it.pagopa.selfcare.notification_manager.api.exception.MailException;
import it.pagopa.selfcare.notification_manager.api.model.MailRequest;

public interface NotificationConnector {

    void sendMessage(MailRequest mailRequest) throws MailException;

}
