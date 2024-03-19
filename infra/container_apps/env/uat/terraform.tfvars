env_short = "u"

tags = {
  CreatedBy   = "Terraform"
  Environment = "Uat"
  Owner       = "SelfCare"
  Source      = "https://github.com/pagopa/selfcare-ms-notification-manager"
  CostCenter  = "TS310 - PAGAMENTI & SERVIZI"
}

container_app = {
  min_replicas = 1
  max_replicas = 2
  scale_rules  = []
  cpu          = 0.5
  memory       = "1Gi"
}

app_settings = [
  {
    name  = "APPLICATIONINSIGHTS_ROLE_NAME"
    value = "ms-notification-manager"
  },
  {
    name  = "NO_REPLY_MAIL"
    value = "Area Riservata <noreply@areariservata.pagopa.it>"
  },
  {
    name  = "JAVA_TOOL_OPTIONS"
    value = "-javaagent:applicationinsights-agent.jar"
  },
  {
    name  = "APPLICATIONINSIGHTS_INSTRUMENTATION_LOGGING_LEVEL"
    value = "OFF"
  },
  {
    name  = "MS_NOTIFICATION_MANAGER_LOG_LEVEL"
    value = "INFO"
  },
  {
    name  = "CUSTOMER_CARE_MAIL"
    value = "pectest@pec.pagopa.it"
  },
  {
    name  = "CUSTOMER_CARE_MAIL_SUBJECT_PREFIX"
    value = "[UAT] "
  },
  {
    name  = "NOTIFICATION_TO_USER_SUBJECT_PREFIX"
    value = "[SELF CARE UAT] "
  },
  {
    name  = "MAIL_CONNECTOR_TYPE"
    value = "aws"
  },
  {
    name  = "AWS_SES_REGION"
    value = "eu-south-1"
  },
  {
    name  = "MAIL_SERVER_SMTP_AUTH"
    value = "false"
  }
]

secrets_names = {
  "AWS_SES_ACCESS_KEY_ID"                 = "aws-ses-access-key-id"
  "AWS_SES_SECRET_ACCESS_KEY"             = "aws-ses-secret-access-key"
  "APPLICATIONINSIGHTS_CONNECTION_STRING" = "appinsights-connection-string"
  "JWT_TOKEN_PUBLIC_KEY"                  = "jwt-public-key"
}
