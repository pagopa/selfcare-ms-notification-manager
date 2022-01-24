package it.pagopa.selfcare.notification_manager.api.model;

import lombok.Data;

@Data
public class MailRequest {
    private String from;
    private String to;
    private String subject;
    private String content;
}
