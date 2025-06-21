package org.practice.deliveryservice.service;

import org.practice.deliveryservice.dto.DeliveryDto;
import org.practice.deliveryservice.model.Delivery;

public interface DeliveryService {

    Delivery createDelivery(DeliveryDto deliveryDto);

}
