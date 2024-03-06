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
          "desiredReplicas" = "3"
          "start"           = "0 8 * * MON-FRI"
          "end"             = "0 19 * * MON-FRI"
          "timezone"        = "Europe/Rome"
        }
        type = "cron"
      }
      name = "cron-scale-rule"
    }
  ]
  cpu    = 1.25
  memory = "2.5Gi"
}

app_settings = [
  {
    name = "APPLICATIONINSIGHTS_ROLE_NAME"
    value = "ms-notification-manager"
  },
  {
    name = "NO_REPLY_MAIL"
    value = "Area Riservata <noreply@areariservata.pagopa.it>"
  },
  {
    name = "JAVA_TOOL_OPTIONS"
    value = "-javaagent:applicationinsights-agent.jar"
  },
 {
    name = "APPLICATIONINSIGHTS_INSTRUMENTATION_LOGGING_LEVEL"
    value = "OFF"
  },
 {
    name = "MS_NOTIFICATION_MANAGER_LOG_LEVEL"
    value = "INFO"
  },
 {
    name = "CUSTOMER_CARE_MAIL"
    value = "areariservata@assistenza.pagopa.it"
  },
 {
    name = "CUSTOMER_CARE_MAIL_SUBJECT_PREFIX"
    value = ""
  },
 {
    name = "NOTIFICATION_TO_USER_SUBJECT_PREFIX"
    value = ""
  },
 {
    name = "MAIL_CONNECTOR_TYPE"
    value = "aws"
 },
 {
    name = "MAIL_SERVER_HOST"
    value = "smtp.gmail.com"
 },
 {
    name = "MAIL_SERVER_PORT"
    value = 587
 },
 {
    name = "AWS_SES_REGION"
    value = "eu-south-1"
 }
]

secrets_names = {
  "MAIL_SERVER_USERNAME"      = "smtp-not-pec-usr"
  "MAIL_SERVER_PASSWORD"      = "smtp-not-pec-psw"
  "AWS_SES_ACCESS_KEY_ID"     = "aws-ses-access-key-id"
  "AWS_SES_SECRET_ACCESS_KEY" = "aws-ses-secret-access-key"
}