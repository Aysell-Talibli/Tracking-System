package org.practice.deliveryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.practice.deliveryservice.dto.DeliveryDto;
import org.practice.deliveryservice.dto.DeliveryEventDto;
import org.practice.deliveryservice.dto.DeliveryResponseDto;
import org.practice.deliveryservice.dto.UpdatedDeliveryDto;
import org.practice.deliveryservice.model.Delivery;
import org.practice.deliveryservice.model.enums.DeliveryStatus;
import org.practice.deliveryservice.repository.DeliveryRepository;
import org.practice.deliveryservice.service.DeliveryService;
import org.practice.deliveryservice.service.KafkaProducerService;
import org.practice.deliveryservice.service.TrackingService;
import org.practice.deliveryservice.service.mapper.DeliveryMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final KafkaProducerService kafkaProducerService;
    private final DeliveryMapper deliveryMapper;
    private final TrackingService trackingService;
    private final ModelMapper modelMapper;

    @Override
    public Delivery createDelivery(DeliveryDto deliveryDto, String email) {
        Delivery delivery=Delivery.builder()
                .senderEmail(email)
                .recipientEmail(deliveryDto.getReceiverEmail())
                .currentLocation(deliveryDto.getLocation())
                .destination(deliveryDto.getDestination())
                .status(DeliveryStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .trackingNumber("PKG-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .build();

        Delivery savedDelivery = deliveryRepository.save(delivery);

        DeliveryEventDto deliveryEventDto=new DeliveryEventDto(
                savedDelivery.getTrackingNumber(),
                savedDelivery.getSenderEmail(),
                savedDelivery.getRecipientEmail(),
                savedDelivery.getStatus().toString()
        );
        kafkaProducerService.sendDeliveryEvent(deliveryEventDto);
        return savedDelivery;
    }


    @Override
    public UpdatedDeliveryDto updateDeliveryStatus(UUID deliveryId, UpdatedDeliveryDto updatedDeliveryDto) {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new RuntimeException("Delivery not found with ID: " + deliveryId));
        delivery.setStatus(updatedDeliveryDto.getStatus());
        delivery.setCurrentLocation(updatedDeliveryDto.getCurrentLocation());
        Delivery updatedDelivery = deliveryRepository.save(delivery);
        System.out.println("current location" + updatedDelivery.getCurrentLocation());
        trackingService.addTrackingHistory(deliveryId,
                updatedDelivery.getStatus(),
                updatedDelivery.getCurrentLocation());


//        DeliveryEventDto deliveryEventDto=new DeliveryEventDto(
//                updatedDelivery.getTrackingNumber(),
//                updatedDelivery.getRecipientEmail(),
//                updatedDelivery.getCurrentLocation(),
//                updatedDelivery.getStatus().toString()
//
//        );
//        kafkaProducerService.sendDeliveryEvent(deliveryEventDto);
        return deliveryMapper.toUpdatedDto(updatedDelivery);

    }


}
