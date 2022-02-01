package it.pagopa.selfcare.notification_manager.core.model;

import lombok.Data;

@Data
public class MessageRequest {

    String content;
    String subject;
    String senderEmail;
}
