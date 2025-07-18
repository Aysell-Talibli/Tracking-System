package org.practice.deliveryservice.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.practice.deliveryservice.dto.DeliveryDto;
import org.practice.deliveryservice.dto.DeliveryResponseDto;
import org.practice.deliveryservice.dto.UpdatedDeliveryDto;
import org.practice.deliveryservice.dto.response.TrackingResponseDto;
import org.practice.deliveryservice.model.Delivery;
import org.practice.deliveryservice.service.DeliveryService;
import org.practice.deliveryservice.service.TrackingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("delivery/create")
    public ResponseEntity<Delivery> createDelivery(@RequestBody @Valid DeliveryDto deliveryDto,
                                                              @RequestHeader("X-User-Email") String email){
        return ResponseEntity.ok(deliveryService.createDelivery(deliveryDto, email));
    }

//    @GetMapping("track/{trackingNumber}")
//    public ResponseEntity<TrackingResponseDto> trackPackage(@PathVariable @NotBlank String trackingNumber){
//        return ResponseEntity.ok(trackingService.trackPackage(trackingNumber));
//    }

    @PutMapping("update/status/{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<UpdatedDeliveryDto> updateStatus(@PathVariable UUID id,
                                                 @RequestBody @Valid UpdatedDeliveryDto updatedDeliveryDto){
        return ResponseEntity.ok(deliveryService.updateDeliveryStatus(id, updatedDeliveryDto));
    }

}
