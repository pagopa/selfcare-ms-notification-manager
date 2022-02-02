package it.pagopa.selfcare.notification_manager.web.model.mapper;

import it.pagopa.selfcare.notification_manager.core.model.MessageRequest;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageDto;

public class MessageMapper {
    public static MessageRequest toMessageRequest(CreateMessageDto messageDto) {
        MessageRequest message = null;
        if (messageDto != null) {
            message = new MessageRequest();
            message.setContent(messageDto.getContent());
            message.setSubject(messageDto.getSubject());
            message.setSenderEmail(messageDto.getSenderEmail());
        }
        return message;
    }
}
