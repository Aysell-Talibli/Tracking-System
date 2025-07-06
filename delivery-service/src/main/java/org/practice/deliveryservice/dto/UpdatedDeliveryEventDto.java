package org.practice.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedDeliveryEventDto {
    private String trackingNumber;
    private String status;
    private String location;
}