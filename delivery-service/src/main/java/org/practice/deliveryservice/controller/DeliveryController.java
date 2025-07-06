package org.practice.deliveryservice.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.practice.deliveryservice.dto.DeliveryDto;
import org.practice.deliveryservice.dto.DeliveryResponseDto;
import org.practice.deliveryservice.dto.UpdatedDeliveryDto;
import org.practice.deliveryservice.model.Delivery;
import org.practice.deliveryservice.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("delivery/create")
    public ResponseEntity<DeliveryResponseDto> createDelivery(@RequestBody @Valid DeliveryDto deliveryDto,
                                                              @RequestHeader("X-User-Email") String email){
        return ResponseEntity.ok(deliveryService.createDelivery(deliveryDto, email));
    }

    @GetMapping("track/{trackingNumber}")
    public ResponseEntity<Delivery> trackPackage(@PathVariable @NotBlank String trackingNumber){
        return ResponseEntity.ok(deliveryService.trackPackage(trackingNumber));
    }

    @PostMapping("update/status/{id}")
    public ResponseEntity<Delivery> updateStatus(@PathVariable UUID id,
                                                 @RequestBody @Valid UpdatedDeliveryDto updatedDeliveryDto){
        return ResponseEntity.ok(deliveryService.updateDeliveryStatus(id, updatedDeliveryDto));
    }


}
