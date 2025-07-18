package org.practice.deliveryservice.dto;

import lombok.*;
import org.practice.deliveryservice.model.enums.DeliveryStatus;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatedDeliveryDto {
    private DeliveryStatus status;
    private String currentLocation;
}
