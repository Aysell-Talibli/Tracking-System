package org.practice.deliveryservice.dto;


import lombok.Data;
import org.practice.deliveryservice.model.enums.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class DeliveryResponseDto {
    private String trackingNumber;
    private DeliveryStatus status;
    private LocalDateTime createdAt;
}
