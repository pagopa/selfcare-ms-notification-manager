package it.pagopa.selfcare.notification_manager.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.pagopa.selfcare.commons.base.security.SelfCareUser;
import it.pagopa.selfcare.notification_manager.core.NotificationService;
import it.pagopa.selfcare.notification_manager.core.exception.MessageRequestException;
import it.pagopa.selfcare.notification_manager.core.model.MessageRequest;
import it.pagopa.selfcare.notification_manager.web.handler.NotificationExceptionsHandler;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageToCustomerCareDto;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageToUserDto;
import it.pagopa.selfcare.notification_manager.web.model.mapper.MessageMapper;
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
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.test.context.TestSecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(value = {NotificationController.class}, excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ContextConfiguration(classes = {
        NotificationController.class,
        NotificationExceptionsHandler.class
})
class NotificationControllerTest {
    private static final String BASE_URL = "/notifications/v1";
    private static final String CONTENT = "test message";
    private static final String SUBJECT = "test subject";
    private static final String RECEIVER_MAIL = "test@mail.com";

    @Autowired
    protected MockMvc mvc;

    @MockBean
    private NotificationService notificationServiceMock;

    @Autowired
    protected ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<MessageRequest> messageRequestCaptor;

    @Test
    void sendNotificationToCustomer() throws Exception {
        //given

        CreateMessageToCustomerCareDto messageDto = new CreateMessageToCustomerCareDto();
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
        Mockito.verify(notificationServiceMock, Mockito.times(1))
                .sendMessageToCustomerCare(messageRequestCaptor.capture());
        Mockito.verifyNoMoreInteractions(notificationServiceMock);

    }

    @Test
    void sendNotificationToCustomer_nullReplyTo() throws Exception {
        //given
        SelfCareUser selfCareUser = SelfCareUser.builder("id").email(null).build();
        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(selfCareUser, null);
        TestSecurityContextHolder.setAuthentication(authenticationToken);
        CreateMessageToCustomerCareDto messageDto = new CreateMessageToCustomerCareDto();
        messageDto.setContent(CONTENT);
        messageDto.setSubject(SUBJECT);
        messageDto.setSenderEmail(null);

        Mockito.doThrow(MessageRequestException.class).when(notificationServiceMock).sendMessageToCustomerCare(MessageMapper.toMessageRequest(messageDto));
        //when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL + "/customer-care")
                .content(objectMapper.writeValueAsString(messageDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        //then
        Assertions.assertEquals(0, result.getResponse().getContentLength());
        Mockito.verify(notificationServiceMock, Mockito.times(1))
                .sendMessageToCustomerCare(messageRequestCaptor.capture());
        Mockito.verifyNoMoreInteractions(notificationServiceMock);

    }

    @Test
    void sendNotificationToUser() throws Exception {
        //given

        CreateMessageToUserDto messageDto = new CreateMessageToUserDto();
        messageDto.setContent(CONTENT);
        messageDto.setSubject(SUBJECT);
        messageDto.setReceiverEmail(RECEIVER_MAIL);
        //when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL + "/user")
                .content(objectMapper.writeValueAsString(messageDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andReturn();
        //then
        Assertions.assertEquals(0, result.getResponse().getContentLength());
        Mockito.verify(notificationServiceMock, Mockito.times(1))
                .sendMessageToUser(messageRequestCaptor.capture());
        Mockito.verifyNoMoreInteractions(notificationServiceMock);
    }

    @Test
    void sendNotificationToUser_nullReceiver() throws Exception {
        //given

        CreateMessageToUserDto messageDto = new CreateMessageToUserDto();
        messageDto.setContent(CONTENT);
        messageDto.setSubject(SUBJECT);
        messageDto.setReceiverEmail(null);
        //when
        MvcResult result = mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL + "/user")
                .content(objectMapper.writeValueAsString(messageDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andReturn();
        //then
        Assertions.assertEquals(0, result.getResponse().getContentLength());
        Mockito.verify(notificationServiceMock, Mockito.times(0))
                .sendMessageToUser(messageRequestCaptor.capture());
        Mockito.verifyNoMoreInteractions(notificationServiceMock);
    }

}