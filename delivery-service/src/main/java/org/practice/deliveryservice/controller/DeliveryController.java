package org.practice.deliveryservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.practice.deliveryservice.dto.DeliveryDto;
import org.practice.deliveryservice.model.Delivery;
import org.practice.deliveryservice.service.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
@RequestMapping("api/deliveries")
public class DeliveryController {
    private final DeliveryService deliveryService;

    @PostMapping("/create")
    public ResponseEntity<Delivery> createDelivery(@RequestBody @Valid DeliveryDto deliveryDto){
        return ResponseEntity.ok(deliveryService.createDelivery(deliveryDto));
    }


}
