package it.pagopa.selfcare.notification_manager.core;

import it.pagopa.selfcare.commons.base.security.SelfCareUser;
import it.pagopa.selfcare.commons.web.security.JwtService;
import it.pagopa.selfcare.notification_manager.api.NotificationConnector;
import it.pagopa.selfcare.notification_manager.api.exception.MailException;
import it.pagopa.selfcare.notification_manager.api.model.MailRequest;
import it.pagopa.selfcare.notification_manager.core.config.NotificationCoreConfig;
import it.pagopa.selfcare.notification_manager.core.exception.MessageRequestException;
import it.pagopa.selfcare.notification_manager.core.model.MessageRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.TestSecurityContextHolder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = {
                NotificationServiceImpl.class,
                NotificationCoreConfig.class
        },
        properties = {
                "CUSTOMER_CARE_MAIL=selfcare@assistenza.pagopa.it",
                "NO_REPLY_MAIL=noreply@pagopa.it",
                "ENV_TARGET=TEST"
        }
)
class NotificationServiceImplTest {

    private static final String CONTENT = "test message";
    private static final String SUBJECT = "test subject";
    private static final String FROM = "noreply@pagopa.it";
    private static final String TO = "selfcare@assistenza.pagopa.it";
    private static final String RECEIVER = "user@email.it";


    @Autowired
    private NotificationServiceImpl notificationService;

    @MockBean
    private NotificationConnector notificationConnectorMock;
    
    @MockBean
    private JwtService jwtServiceMock;

    @Captor
    private ArgumentCaptor<MailRequest> mailRequestCaptor;


    @BeforeEach
    void beforeEach() {
        TestSecurityContextHolder.clearContext();
    }

    @Test
    void authentication_nullAuth() {
        //given
        MessageRequest request = new MessageRequest();
        request.setContent(CONTENT);
        request.setSubject(SUBJECT);
        //when
        Executable executable = () -> notificationService.sendMessageToCustomerCare(request);
        //then
        IllegalStateException illegalStateException = Assertions.assertThrows(IllegalStateException.class, executable);
        Assertions.assertEquals("Authentication is required", illegalStateException.getMessage());

        Mockito.verifyNoInteractions(notificationConnectorMock);
    }

    @Test
    void authentication_nullPrincipal() {
        //given
        Authentication authentication = new TestingAuthenticationToken(null, null);
        TestSecurityContextHolder.setAuthentication(authentication);
        MessageRequest request = new MessageRequest();
        request.setContent(CONTENT);
        request.setSubject(SUBJECT);
        //when
        Executable executable = () -> notificationService.sendMessageToCustomerCare(request);
        //then
        IllegalStateException illegalStateException = Assertions.assertThrows(IllegalStateException.class, executable);
        Assertions.assertEquals("Not SelfCareUSer principal", illegalStateException.getMessage());

        Mockito.verifyNoInteractions(notificationConnectorMock);
    }

    @Test
    void authenticate_sendMessageMessageToCustomerCare() throws MailException {
        //given
        SelfCareUser selfCareUser = SelfCareUser.builder("id").email("test@example.com").fiscalCode("fiscalCode").build();
        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(selfCareUser, null);
        TestSecurityContextHolder.setAuthentication(authenticationToken);
        MessageRequest request = new MessageRequest();
        request.setContent(CONTENT);
        request.setSubject(SUBJECT);
        //when
        notificationService.sendMessageToCustomerCare(request);
        //then
        Assertions.assertEquals(SelfCareUser.class, authenticationToken.getPrincipal().getClass());
        Mockito.verify(notificationConnectorMock, Mockito.times(1))
                .sendMessage(mailRequestCaptor.capture());
        MailRequest mailRequest = mailRequestCaptor.getValue();
        Assertions.assertEquals("TEST - " + SUBJECT + " " + selfCareUser.getFiscalCode(), mailRequest.getSubject());
        Assertions.assertEquals(CONTENT, mailRequest.getContent());
        Assertions.assertEquals(FROM, mailRequest.getFrom());
        Assertions.assertEquals(TO, mailRequest.getTo());
        Assertions.assertEquals(selfCareUser.getEmail(), mailRequest.getReplyTo().get());
        Mockito.verifyNoMoreInteractions(notificationConnectorMock);
    }

    @Test
    void sendMessageToCustomerCare_nullRequest() {
        //given
        MessageRequest request = null;
        //when
        Executable executable = () -> notificationService.sendMessageToCustomerCare(request);
        //then
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class, executable);
        Assertions.assertEquals("Message request must not be null", exception.getMessage());
        Mockito.verifyNoInteractions(notificationConnectorMock);
    }

    @Test
    void sendMessageMessageToCustomerCare_NullReplyTo() {
        //given
        SelfCareUser selfCareUser = SelfCareUser.builder("id").build();
        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(selfCareUser, null);
        TestSecurityContextHolder.setAuthentication(authenticationToken);
        MessageRequest request = new MessageRequest();
        request.setContent(CONTENT);
        request.setSubject(SUBJECT);
        request.setSenderEmail(null);
        //when
        Executable executable = () -> notificationService.sendMessageToCustomerCare(request);
        //then
        MessageRequestException e = Assertions.assertThrows(MessageRequestException.class, executable);
        Assertions.assertEquals("Missing replyTo address", e.getMessage());
    }

    @Test
    void sendMessageToCustomerCare_nullPrincipalEmail() throws MailException {
        //given
        SelfCareUser selfCareUser = SelfCareUser.builder("id").email(null).build();
        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken(selfCareUser, null);
        TestSecurityContextHolder.setAuthentication(authenticationToken);
        MessageRequest request = new MessageRequest();
        request.setContent(CONTENT);
        request.setSubject(SUBJECT);
        request.setSenderEmail("test@example.com");
        //when
        notificationService.sendMessageToCustomerCare(request);
        //then
        Assertions.assertEquals(SelfCareUser.class, authenticationToken.getPrincipal().getClass());
        Mockito.verify(notificationConnectorMock, Mockito.times(1))
                .sendMessage(mailRequestCaptor.capture());
        MailRequest mailRequest = mailRequestCaptor.getValue();
        Assertions.assertEquals("TEST - " + SUBJECT, mailRequest.getSubject());
        Assertions.assertEquals(CONTENT, mailRequest.getContent());
        Assertions.assertEquals(FROM, mailRequest.getFrom());
        Assertions.assertEquals(TO, mailRequest.getTo());
        Assertions.assertEquals("test@example.com", mailRequest.getReplyTo().get());
        Mockito.verifyNoMoreInteractions(notificationConnectorMock);
    }

    @Test
    void sendMessageToUser() throws MailException {
        //given
        MessageRequest request = new MessageRequest();
        request.setReceiverEmail(RECEIVER);
        request.setContent(CONTENT);
        request.setSubject(SUBJECT);
        //when
        notificationService.sendMessageToUser(request);
        //then
        Mockito.verify(notificationConnectorMock, Mockito.times(1))
                .sendMessage(mailRequestCaptor.capture());
        MailRequest mailRequest = mailRequestCaptor.getValue();

        Assertions.assertEquals("TEST - " + SUBJECT, mailRequest.getSubject());
        Assertions.assertEquals(CONTENT, mailRequest.getContent());
        Assertions.assertEquals(FROM, mailRequest.getFrom());
        Assertions.assertEquals(RECEIVER, mailRequest.getTo());
        Mockito.verifyNoMoreInteractions(notificationConnectorMock);

    }


}
