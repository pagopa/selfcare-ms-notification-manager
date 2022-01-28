package it.pagopa.selfcare.notification_manager.web.model.mapper;

import it.pagopa.selfcare.commons.utils.TestUtils;
import it.pagopa.selfcare.notification_manager.core.model.MessageRequest;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MessageMapperTest {
    @Test
    void toMessageRequest_notNull() {
        //given
        CreateMessageDto messageDto = TestUtils.mockInstance(new CreateMessageDto());
        //when
        MessageRequest messageRequest = MessageMapper.toMessageRequest(messageDto);
        //then
        Assertions.assertEquals(messageDto.getContent(), messageRequest.getContent());
        Assertions.assertEquals(messageDto.getSubject(), messageRequest.getSubject());
        TestUtils.reflectionEqualsByName(messageRequest, messageDto);
    }

    @Test
    void toMessageRequest_null() {
        //give
        //when
        MessageRequest messageRequest = MessageMapper.toMessageRequest(null);
        //then
        Assertions.assertNull(messageRequest);
    }
}
