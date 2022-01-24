package it.pagopa.selfcare.commons.connector.email;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = {})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CustomerCareNotificationServiceTest {
    private static final String FROM = "bgalgamu@nttdata.com";
    private static final String TO = "bgalgamu@nttdata.com";
    private static final String MESSAGE = "test message";
    private static final String SUBJECT = " test";


    @Autowired
    private CustomerCareNotificationService notificationService;

    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("bgalgamu", "pagopa"))
            .withPerMethodLifecycle(false);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void sendEmail_WithCorrectPayload() throws Exception {
//        notificationService.sendMessage();
    }
}