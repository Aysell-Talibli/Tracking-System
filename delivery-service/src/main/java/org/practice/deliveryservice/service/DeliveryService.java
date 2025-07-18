package org.practice.deliveryservice.service;

import org.practice.deliveryservice.dto.DeliveryDto;
import org.practice.deliveryservice.dto.DeliveryResponseDto;
import org.practice.deliveryservice.dto.UpdatedDeliveryDto;
import org.practice.deliveryservice.model.Delivery;

import java.util.UUID;

public interface DeliveryService {

    Delivery createDelivery(DeliveryDto deliveryDto, String email);

    UpdatedDeliveryDto updateDeliveryStatus(UUID deliveryId, UpdatedDeliveryDto updatedDeliveryDto);

}
