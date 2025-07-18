package org.practice.deliveryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.practice.deliveryservice.model.enums.DeliveryStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingResponseDto {
    private String trackingNumber;
    private DeliveryStatus status;
    private String currentLocation;
    private List<TrackingHistoryDto> trackingHistory;
}
