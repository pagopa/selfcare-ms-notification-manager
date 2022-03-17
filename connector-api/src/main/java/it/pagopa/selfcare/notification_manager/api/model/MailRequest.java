package it.pagopa.selfcare.notification_manager.api.model;

import lombok.Data;

import java.util.Optional;

@Data
public class MailRequest {
    private String from;
    private String to;
    private String subject;
    private String content;
    private Optional<String> replyTo;

    public void setReplyTo(Optional<String> replyTo) {
        this.replyTo = replyTo == null ? Optional.empty() : replyTo;
    }
}
