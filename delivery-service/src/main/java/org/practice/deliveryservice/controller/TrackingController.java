package org.practice.deliveryservice.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.practice.deliveryservice.dto.response.TrackingResponseDto;
import org.practice.deliveryservice.service.TrackingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class TrackingController {

    private final TrackingService trackingService;

    @GetMapping("track/{trackingNumber}")
    public ResponseEntity<TrackingResponseDto> trackPackage(@PathVariable @NotBlank String trackingNumber){
        return ResponseEntity.ok(trackingService.trackPackage(trackingNumber));
    }
}
