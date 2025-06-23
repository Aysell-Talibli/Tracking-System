package org.practice.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryEventDto {
    private String trackingNumber;
    private String senderEmail;
    private String recipientEmail;
    private String status;
}