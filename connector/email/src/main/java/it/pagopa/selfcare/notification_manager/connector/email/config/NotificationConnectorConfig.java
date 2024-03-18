package it.pagopa.selfcare.notification_manager.connector.email.config;

import it.pagopa.selfcare.notification_manager.api.NotificationConnector;
import it.pagopa.selfcare.notification_manager.connector.email.NotificationConnectorAws;
import it.pagopa.selfcare.notification_manager.connector.email.NotificationConnectorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
public class NotificationConnectorConfig {

    @Autowired
    private ApplicationContext appContext;
    @Bean
    public NotificationConnector notificationConnector(@Value("${spring.mail.connector.type}") String connectorType) {
        System.out.println("connectorType: " + connectorType);
        System.out.println("connectorType aws: " + "aws".equalsIgnoreCase(connectorType));
        if("aws".equalsIgnoreCase(connectorType)){
            SesClient sesClient = appContext.getBean(SesClient.class);
            return new NotificationConnectorAws(sesClient);
        }

        JavaMailSender mailSender = appContext.getBean(JavaMailSender.class);
        return new NotificationConnectorImpl(mailSender);
    }
}
