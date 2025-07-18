package org.practice.deliveryservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackingHistoryDto {
    private String status;
    private String location;
    private String updatedAt;
}
