# selfcare-ms-notification-manager

## Configuration Properties

| **Application properties** |
|:--------------------------:|

| **Property** | **Enviroment Variable** | **Default** | **Required** |
|--------------|-------------------------|-------------|:------------:|
|server.port|MS_NOTIFICATION_MANAGER_SERVER_PORT|<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |
|spring.application.name| n/a |<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |
|spring.application.version| n/a |<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |
|spring.profiles.include| n/a |<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |
|spring.zipkin.enabled| n/a |<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |
|spring.sleuth.baggage.remote-fields| n/a |<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |
|spring.sleuth.baggage.correlation-fields| n/a |<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |
|info.build.artifact| n/a |<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |
|info.build.name| n/a |<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |
|info.build.description| n/a |<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |
|info.build.version| n/a |<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |
|logging.level.it.pagopa.selfcare| MS_NOTIFICATION_MANAGER_LOG_LEVEL |<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |
|logging.pattern.level| n/a |<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/app/src/main/resources/config/application.yml)| yes |

| **Email Configurations** |
|:------------------------:|

| **Property** | **Enviroment Variable** | **Default** | **Required** |
|--------------|-------------------------|-------------|:------------:|
|spring.mail.host|MAIL_SERVER_HOST|<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/connector/email/src/main/resources/config/email.properties)| yes |
|spring.mail.port|MAIL_SERVER_PORT|<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/connector/email/src/main/resources/config/email.properties)| yes |
|spring.mail.username|MAIL_SERVER_USERNAME|<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/connector/email/src/main/resources/config/email.properties)| yes |
|spring.mail.password|MAIL_SERVER_PASSWORD|<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/connector/email/src/main/resources/config/email.properties)| yes |
|spring.mail.properties.mail.smtp.auth|MAIL_SERVER_SMTP_AUTH|<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/connector/email/src/main/resources/config/email.properties)| yes|
|spring.mail.properties.mail.smtp.starttls.enable|MAIL_SERVER_SMTP_TLS_ENABLE|<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/connector/email/src/main/resources/config/email.properties)| yes|
|spring.mail.protocol|MAIL_SERVER_PROTOCOL|<a name= "default property"></a>[default_property](https://github.com/pagopa/selfcare-ms-notification-manager/blob/release-dev/connector/email/src/main/resources/config/email.properties)| yes|