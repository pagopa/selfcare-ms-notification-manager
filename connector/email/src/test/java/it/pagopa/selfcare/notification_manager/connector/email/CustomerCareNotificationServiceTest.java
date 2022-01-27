package it.pagopa.selfcare.notification_manager.connector.email;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import it.pagopa.selfcare.notification_manager.api.exception.MailException;
import it.pagopa.selfcare.notification_manager.api.model.MailRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailSenderAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.mail.internet.MimeMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = {
                MailSenderAutoConfiguration.class,
                CustomerCareNotificationService.class
        })
@TestPropertySource(value = "classpath:config/email-test.properties")
class CustomerCareNotificationServiceTest {
    private static final String FROM = "noreply@pagopa.it";
    private static final String TO = "selfcare_ssistenza.pagopa.com";
    private static final String MESSAGE = "test message";
    private static final String SUBJECT = " test";

    @Autowired
    private CustomerCareNotificationService notificationService;

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("username", "password"))
            .withPerMethodLifecycle(false);

    @Test
    void sendEmail_WithCorrectPayload() throws Exception {
        //given
        MailRequest mail = new MailRequest();
        mail.setTo(TO);
        mail.setFrom(FROM);
        mail.setContent(MESSAGE);
        mail.setSubject(SUBJECT);
        mail.setReplyTo("bgalgamu@nttdata.com");
        //when
        notificationService.sendMessage(mail);
        //then
        MimeMessage receivedMessage = greenMail.getReceivedMessages()[0];
        Assertions.assertEquals(MESSAGE, GreenMailUtil.getBody(receivedMessage));
        Assertions.assertEquals(1, receivedMessage.getAllRecipients().length);
        Assertions.assertEquals(TO, receivedMessage.getAllRecipients()[0].toString());
        Assertions.assertEquals(FROM, receivedMessage.getFrom()[0].toString());
        Assertions.assertEquals("bgalgamu@nttdata.com", receivedMessage.getReplyTo()[0].toString());

    }


    @Test
    void sendEmail_WithException() {
        //given
        MailRequest mail = new MailRequest();
        mail.setTo(null);
        mail.setFrom(null);
        mail.setContent(MESSAGE);
        mail.setSubject(SUBJECT);
        mail.setReplyTo("bgalgamui@nttdata.com");
        //when
        Executable executable = () -> notificationService.sendMessage(mail);
        //then
        Assertions.assertThrows(MailException.class, executable);


    }

    @Test
    void sendEmail_nullRequestMail() {
        //given
        MailRequest mail = null;
        //when
        Executable executable = () -> notificationService.sendMessage(mail);
        //then
        IllegalArgumentException assertThrows = Assertions.assertThrows(IllegalArgumentException.class, executable);
        Assertions.assertEquals("the MailRequest must not be null", assertThrows.getMessage());
    }
}