package org.practice.deliveryservice.repository;

import org.practice.deliveryservice.model.TrackingHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TrackingRepository extends JpaRepository<TrackingHistory, UUID> {
}
