package it.pagopa.selfcare.notification_manager.web.controller;

import io.swagger.annotations.Api;
import it.pagopa.selfcare.notification_manager.core.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
@Api(tags = "notification_manager")
public class NotificationController {

    private final NotificationService notificationService;


    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/customer-care")
    @ResponseStatus(HttpStatus.OK)
    void sendNotificationToCustomerCare() {

    }

}
