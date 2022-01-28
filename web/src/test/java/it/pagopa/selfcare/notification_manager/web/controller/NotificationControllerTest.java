package it.pagopa.selfcare.notification_manager.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.pagopa.selfcare.notification_manager.core.NotificationService;
import it.pagopa.selfcare.notification_manager.core.model.MessageRequest;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(value = {NotificationController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {
        NotificationController.class
})
class NotificationControllerTest {
    private static final String BASE_URL = "/notifications/v1";
    private static final String CONTENT = "test message";
    private static final String SUBJECT = "test subject";

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private NotificationService notificationService;

    @Autowired
    protected ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<MessageRequest> messageRequestCaptor;

    @Test
    void sendNotificationToCustomer() throws Exception {
        //given
        CreateMessageDto messageDto = new CreateMessageDto();
        messageDto.setContent(CONTENT);
        messageDto.setSubject(SUBJECT);
        //when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL + "/customer-care")
                .content(objectMapper.writeValueAsString(messageDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
        //then
        Assertions.assertEquals(0, result.getResponse().getContentLength());
        Mockito.verify(notificationService, Mockito.times(1))
                .sendMessage(messageRequestCaptor.capture());
        Mockito.verifyNoMoreInteractions(notificationService);

    }


}