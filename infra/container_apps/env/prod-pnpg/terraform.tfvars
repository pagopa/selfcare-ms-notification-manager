is_pnpg   = true
env_short = "p"

tags = {
  CreatedBy   = "Terraform"
  Environment = "Prod"
  Owner       = "SelfCare"
  Source      = "https://github.com/pagopa/selfcare-ms-notification-manager"
  CostCenter  = "TS310 - PAGAMENTI & SERVIZI"
}

container_app = {
  min_replicas = 1
  max_replicas = 5
  scale_rules = [
    {
      custom = {
        metadata = {
          "desiredReplicas" = "2"
          "start"           = "0 8 * * MON-FRI"
          "end"             = "0 19 * * MON-FRI"
          "timezone"        = "Europe/Rome"
        }
        type = "cron"
      }
      name = "cron-scale-rule"
    }
  ]
  cpu    = 0.5
  memory = "1Gi"
}

workload_profile_name = null

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
    value = "areariservata@assistenza.pagopa.it"
  },
  {
    name  = "CUSTOMER_CARE_MAIL_SUBJECT_PREFIX"
    value = ""
  },
  {
    name  = "NOTIFICATION_TO_USER_SUBJECT_PREFIX"
    value = ""
  },
  {
    name  = "MAIL_CONNECTOR_TYPE"
    value = "aws"
  },
  {
    name  = "MAIL_SERVER_SMTP_AUTH"
    value = false
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