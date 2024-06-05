package it.pagopa.selfcare.notification_manager.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class CreateMessageToUsersDto {

    @ApiModelProperty(value = "${swagger.notification_manager.model.content}", required = true)
    @JsonProperty(required = true)
    @NotBlank
    String content;

    @ApiModelProperty(value = "${swagger.notification_manager.model.subject}", required = true)
    @JsonProperty(required = true)
    @NotBlank
    String subject;

    @ApiModelProperty(value = "${swagger.notification_manager.model.receiverEmail}")
    List<String> receiverEmails;
}
