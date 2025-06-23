package org.practice.deliveryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.practice.deliveryservice.dto.DeliveryDto;
import org.practice.deliveryservice.dto.DeliveryEventDto;
import org.practice.deliveryservice.model.Delivery;
import org.practice.deliveryservice.model.enums.DeliveryStatus;
import org.practice.deliveryservice.repository.DeliveryRepository;
import org.practice.deliveryservice.service.DeliveryService;
import org.practice.deliveryservice.service.KafkaProducerService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public Delivery createDelivery(DeliveryDto deliveryDto) {
        Delivery delivery=Delivery.builder()
                .senderEmail(deliveryDto.getSenderEmail())
                .recipientEmail(deliveryDto.getReceiverEmail())
                .currentLocation(deliveryDto.getLocation())
                .destination(deliveryDto.getDestination())
                .status(DeliveryStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .tracking_number("PKG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .build();

        Delivery savedDelivery = deliveryRepository.save(delivery);

        DeliveryEventDto deliveryEventDto=new DeliveryEventDto(
                savedDelivery.getTracking_number(),
                savedDelivery.getSenderEmail(),
                savedDelivery.getRecipientEmail(),
                savedDelivery.getStatus().toString()
        );
        kafkaProducerService.sendDeliveryEvent(deliveryEventDto);
        return savedDelivery;
    }
}
