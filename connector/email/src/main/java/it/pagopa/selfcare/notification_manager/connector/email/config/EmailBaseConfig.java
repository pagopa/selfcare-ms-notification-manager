package it.pagopa.selfcare.notification_manager.connector.email.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:config/email.properties")
public class EmailBaseConfig {
}
