package org.practice.deliveryservice.repository;

import org.practice.deliveryservice.model.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryRepository extends JpaRepository<Delivery, UUID> {
    Delivery findByTrackingNumber(String trackingNumber);

}
