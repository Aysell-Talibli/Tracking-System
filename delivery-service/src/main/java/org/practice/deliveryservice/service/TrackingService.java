package org.practice.deliveryservice.service;

import org.practice.deliveryservice.dto.response.TrackingHistoryDto;
import org.practice.deliveryservice.dto.response.TrackingResponseDto;
import org.practice.deliveryservice.model.enums.DeliveryStatus;

import java.util.UUID;

public interface TrackingService {
    TrackingResponseDto trackPackage(String trackingNumber);
    public TrackingHistoryDto addTrackingHistory(UUID deliveryId, DeliveryStatus status, String location);


}
