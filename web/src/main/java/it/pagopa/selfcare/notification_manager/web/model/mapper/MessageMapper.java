package it.pagopa.selfcare.notification_manager.web.model.mapper;

import it.pagopa.selfcare.notification_manager.core.model.MessageRequest;
import it.pagopa.selfcare.notification_manager.core.model.MultiReceiverMessageRequest;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageToCustomerCareDto;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageToUserDto;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageToUsersDto;

public class MessageMapper {

    public static MessageRequest toMessageRequest(CreateMessageToCustomerCareDto messageDto) {
        MessageRequest message = null;
        if (messageDto != null) {
            message = new MessageRequest();
            message.setContent(messageDto.getContent());
            message.setSubject(messageDto.getSubject());
            message.setSenderEmail(messageDto.getSenderEmail());
        }
        return message;
    }

    public static MessageRequest toMessageRequest(CreateMessageToUserDto messageDto) {
        MessageRequest message = null;
        if (messageDto != null) {
            message = new MessageRequest();
            message.setContent(messageDto.getContent());
            message.setSubject(messageDto.getSubject());
            message.setReceiverEmail(messageDto.getReceiverEmail());
        }
        return message;
    }

    public static MultiReceiverMessageRequest toMessageRequest(CreateMessageToUsersDto messageDto) {
        MultiReceiverMessageRequest message = null;
        if (messageDto != null) {
            message = new MultiReceiverMessageRequest();
            message.setContent(messageDto.getContent());
            message.setSubject(messageDto.getSubject());
            message.setReceiverEmail(messageDto.getReceiverEmails());
        }
        return message;
    }
}
