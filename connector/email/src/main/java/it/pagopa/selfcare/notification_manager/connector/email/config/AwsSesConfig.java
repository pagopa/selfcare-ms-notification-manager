package it.pagopa.selfcare.notification_manager.connector.email.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;

@Configuration
@PropertySource("classpath:config/email.properties")
@ConfigurationProperties(prefix = "aws.ses")
@Data
public class AwsSesConfig {

    private String secretId;
    private String secretKey;
    private String region;

    @Bean
    public SesClient sesClient(AwsSesConfig awsSesConfig) {

        StaticCredentialsProvider staticCredentials = StaticCredentialsProvider
                .create(AwsBasicCredentials.create(awsSesConfig.getSecretId(), awsSesConfig.getSecretKey()));

        return SesClient.builder()
                .region(Region.of(awsSesConfig.getRegion()))
                .credentialsProvider(staticCredentials)
                .build();
    }
}
