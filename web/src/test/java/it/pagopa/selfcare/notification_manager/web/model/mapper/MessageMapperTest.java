package it.pagopa.selfcare.notification_manager.web.model.mapper;

import it.pagopa.selfcare.commons.utils.TestUtils;
import it.pagopa.selfcare.notification_manager.core.model.MessageRequest;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageToCustomerCareDto;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageToUserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MessageMapperTest {
    @Test
    void toMessageRequest_notNullCustomerCare() {
        //given
        CreateMessageToCustomerCareDto messageDto = TestUtils.mockInstance(new CreateMessageToCustomerCareDto());
        //when
        MessageRequest messageRequest = MessageMapper.toMessageRequest(messageDto);
        //then
        Assertions.assertEquals(messageDto.getContent(), messageRequest.getContent());
        Assertions.assertEquals(messageDto.getSubject(), messageRequest.getSubject());
        Assertions.assertEquals(messageDto.getSenderEmail(), messageRequest.getSenderEmail());
        TestUtils.reflectionEqualsByName(messageRequest, messageDto);
    }

    @Test
    void toMessageRequest_notNullUser() {
        //given
        CreateMessageToUserDto messageDto = TestUtils.mockInstance(new CreateMessageToUserDto());
        //when
        MessageRequest messageRequest = MessageMapper.toMessageRequest(messageDto);
        //then
        Assertions.assertEquals(messageDto.getContent(), messageRequest.getContent());
        Assertions.assertEquals(messageDto.getSubject(), messageRequest.getSubject());
        Assertions.assertEquals(messageDto.getReceiverEmail(), messageRequest.getReceiverEmail());
        TestUtils.reflectionEqualsByName(messageRequest, messageDto);
    }

    @Test
    void toMessageRequest_nullCustomerCare() {
        //give
        CreateMessageToCustomerCareDto messageToCustomerCareDto = null;
        //when
        MessageRequest messageRequest = MessageMapper.toMessageRequest(messageToCustomerCareDto);
        //then
        Assertions.assertNull(messageRequest);
    }

    @Test
    void toMessageRequest_nullUser() {
        //give
        CreateMessageToUserDto messageToUserDto = null;
        //when
        MessageRequest messageRequest = MessageMapper.toMessageRequest(messageToUserDto);
        //then
        Assertions.assertNull(messageRequest);
    }
}
