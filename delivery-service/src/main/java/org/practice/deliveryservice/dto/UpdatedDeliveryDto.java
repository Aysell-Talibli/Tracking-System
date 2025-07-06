package org.practice.deliveryservice.dto;

import lombok.Data;
import org.practice.deliveryservice.model.enums.DeliveryStatus;

@Data
public class UpdatedDeliveryDto {
    private DeliveryStatus status;
    private String location;
}
