package org.practice.deliveryservice.dto;

import lombok.Data;

@Data
public class DeliveryDto {
    private String senderEmail;
    private String ReceiverEmail;
    private String location;
    private String destination;
}
