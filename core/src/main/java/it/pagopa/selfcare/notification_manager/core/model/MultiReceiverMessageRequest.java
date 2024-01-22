package it.pagopa.selfcare.notification_manager.core.model;

import lombok.Data;

import java.util.List;

@Data
public class MultiReceiverMessageRequest {

    String content;
    String subject;
    String senderEmail;
    List<String> receiverEmail;
}
