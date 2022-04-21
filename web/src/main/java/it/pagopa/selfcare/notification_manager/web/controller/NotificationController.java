package it.pagopa.selfcare.notification_manager.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.pagopa.selfcare.notification_manager.core.NotificationService;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageToCustomerCareDto;
import it.pagopa.selfcare.notification_manager.web.model.CreateMessageToUserDto;
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
                                                CreateMessageToCustomerCareDto createMessageToCustomerCareDto) {
        log.trace("sendNotificationToCustomerCare start");
        log.debug("createMessageDto = {}", createMessageToCustomerCareDto);
        notificationService.sendMessageToCustomerCare(MessageMapper.toMessageRequest(createMessageToCustomerCareDto));
        log.trace("sendNotificationToCustomerCare end");

    }


    @PostMapping(value = "/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "", notes = "${swagger.notification_manager.notifications.api.sendNotificationToUser}")
    void sendNotificationToUser(@ApiParam("${swagger.notification_manager.notifications.model.messageRequest}")
                                @RequestBody
                                @Valid
                                        CreateMessageToUserDto createMessageToUserDto) {
        log.trace("sendNotificationToUser start");
        log.debug("sendNotificationToUser createMessageToUserDto = {}", createMessageToUserDto);
        notificationService.sendMessageToUser(MessageMapper.toMessageRequest(createMessageToUserDto));
        log.trace("sendNotificationToUser end");

    }

}
