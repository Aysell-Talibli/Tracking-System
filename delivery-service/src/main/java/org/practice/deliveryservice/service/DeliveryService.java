package org.practice.deliveryservice.service;

import org.practice.deliveryservice.dto.DeliveryDto;
import org.practice.deliveryservice.dto.DeliveryResponseDto;
import org.practice.deliveryservice.dto.UpdatedDeliveryDto;
import org.practice.deliveryservice.model.Delivery;

import java.util.UUID;

public interface DeliveryService {

    DeliveryResponseDto createDelivery(DeliveryDto deliveryDto, String email);
    Delivery trackPackage(String trackingNumber);

    Delivery updateDeliveryStatus(UUID deliveryId, UpdatedDeliveryDto updatedDeliveryDto);

}
