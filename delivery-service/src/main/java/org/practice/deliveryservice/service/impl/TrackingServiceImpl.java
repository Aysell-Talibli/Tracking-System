package org.practice.deliveryservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.practice.deliveryservice.dto.response.TrackingHistoryDto;
import org.practice.deliveryservice.dto.response.TrackingResponseDto;
import org.practice.deliveryservice.model.Delivery;
import org.practice.deliveryservice.model.TrackingHistory;
import org.practice.deliveryservice.model.enums.DeliveryStatus;
import org.practice.deliveryservice.repository.DeliveryRepository;
import org.practice.deliveryservice.repository.TrackingRepository;
import org.practice.deliveryservice.service.TrackingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrackingServiceImpl implements TrackingService {
    private final DeliveryRepository deliveryRepository;
    private final TrackingRepository trackingRepository;
    private final ModelMapper modelMapper;
    @Override
    public TrackingResponseDto trackPackage(String trackingNumber) {
        Delivery delivery=deliveryRepository.findByTrackingNumber(trackingNumber);
        List<TrackingHistory> history=delivery.getTrackingHistory();

        return TrackingResponseDto.builder()
                .trackingNumber(trackingNumber)
                .status(delivery.getStatus())
                .currentLocation(getCurrentLocation(history))
                .trackingHistory(history.stream()
                        .map(h -> modelMapper.map(h, TrackingHistoryDto.class))
                        .collect(Collectors.toList()))
                .build();
    }
    @Override
    public TrackingHistoryDto addTrackingHistory(UUID deliveryId, DeliveryStatus status, String location){
        Delivery delivery = deliveryRepository.findById(deliveryId).orElseThrow(() ->
                new RuntimeException("Delivery not found with id: " + deliveryId));

        TrackingHistory trackingHistory = TrackingHistory.builder()
                .delivery(delivery)
                .status(status)
                .location(location)
                .updatedAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .build();
        trackingHistory=trackingRepository.save(trackingHistory);
        return modelMapper.map(trackingHistory, TrackingHistoryDto.class);
    }

    private String getCurrentLocation(List<TrackingHistory> history){
        return history.stream()
                .findFirst()
                .map(TrackingHistory::getLocation)
                .orElse("Unknown");
    }
}
