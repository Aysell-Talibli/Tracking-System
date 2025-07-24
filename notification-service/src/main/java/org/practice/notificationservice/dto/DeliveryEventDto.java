package org.practice.notificationservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeliveryEventDto {
    private String trackingNumber;
    private String senderEmail;
    private String recipientEmail;
    private String status;
}