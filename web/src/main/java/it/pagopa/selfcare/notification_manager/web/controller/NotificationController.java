package it.pagopa.selfcare.notification_manager.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.pagopa.selfcare.notification_manager.core.NotificationService;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageDto;
import it.pagopa.selfcare.notification_manager.web.model.mapper.MessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/notifications/v1")
@Api(tags = "notification-manager")
public class NotificationController {

    private final NotificationService notificationService;


    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping(value = "/customer-care")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "", notes = "${swagger.notification_manager.notifications.api.sendNotificationToCustomerCare}")
    void sendNotificationToCustomerCare(@ApiParam("${swagger.notification_manager.notifications.model.messageRequest}")
                                        @RequestBody
                                        @Valid
                                                CreateMessageDto createMessageDto) {
        log.trace("NotificationController.sendNotificationToCustomerCare start");
        log.debug("NotificationController.sendNotificationToCustomerCare createMessageDto = {}", createMessageDto);
        notificationService.sendMessage(MessageMapper.toMessageRequest(createMessageDto));
        log.trace("NotificationController.sendNotificationToCustomerCare end");

    }

}
