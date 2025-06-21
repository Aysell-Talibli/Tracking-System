package org.practice.deliveryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.practice.deliveryservice.dto.DeliveryDto;
import org.practice.deliveryservice.model.Delivery;
import org.practice.deliveryservice.model.enums.DeliveryStatus;
import org.practice.deliveryservice.repository.DeliveryRepository;
import org.practice.deliveryservice.service.DeliveryService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Override
    public Delivery createDelivery(DeliveryDto deliveryDto) {
        Delivery delivery=Delivery.builder()
                .senderEmail(deliveryDto.getSenderEmail())
                .recipientEmail(deliveryDto.getReceiverEmail())
                .currentLocation(deliveryDto.getLocation())
                .destination(deliveryDto.getDestination())
                .status(DeliveryStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .tracking_number(UUID.randomUUID().toString())
                .build();
        return deliveryRepository.save(delivery);
    }
}
