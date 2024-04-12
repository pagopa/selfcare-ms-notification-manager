package it.pagopa.selfcare.notification_manager.api.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Optional;

@Data
public class MailRequest {
    @Pattern(regexp = "[a-zA-Z]*", message = "Name can only have letters")
    private String from;
    @Email(
            message = "Email is not valid",
            regexp =
                    "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @NotEmpty(message = "Email cannot be empty")
    private String to;
    @Pattern(regexp = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]*", message = "Name can only have letters")
    private String subject;
    private String content;
    private Optional<String> replyTo;

    public void setReplyTo(Optional<String> replyTo) {
        this.replyTo = replyTo == null ? Optional.empty() : replyTo;
    }
}
