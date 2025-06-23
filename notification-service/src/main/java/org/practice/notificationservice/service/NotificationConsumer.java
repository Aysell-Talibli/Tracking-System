package org.practice.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);
    @KafkaListener(topics = "delivery-events", groupId = "notification-group")
    public void listen(String message) {
        log.info("Notification service received message {}", message);

    }}