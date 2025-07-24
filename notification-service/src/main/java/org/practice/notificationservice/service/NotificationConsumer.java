package org.practice.notificationservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.practice.notificationservice.dto.DeliveryEventDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {
    private static final Logger log = LoggerFactory.getLogger(NotificationConsumer.class);
    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    @KafkaListener(topics = "delivery-events", groupId = "notification-group")
    public void listen(String message) {
        try{
        log.info("Notification service received message {}", message);
        DeliveryEventDto deliveryEventDto=objectMapper.readValue(message, DeliveryEventDto.class);

        emailService.sendDeliveryStatusEmail(
                deliveryEventDto.getSenderEmail(),
                deliveryEventDto.getTrackingNumber(),
                deliveryEventDto.getRecipientEmail()
        );
        } catch (Exception e) {
            log.error("Failed to process delivery event message", e);
        }

    }}