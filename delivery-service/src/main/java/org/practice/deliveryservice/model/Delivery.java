package org.practice.deliveryservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.practice.deliveryservice.model.enums.DeliveryStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Table(name="deliveries", schema = "delivery_service")
public class Delivery {

    @Id
    @GeneratedValue
    private UUID id;

    @NotNull
    @Column(name="tracking_number", unique = true)
    private String trackingNumber;

    @NotNull
    @Column(name="sender_email")
    private String senderEmail;

    @NotNull
    @Column(name="recipient_email")
    private String recipientEmail;

    @NotNull
    @Column(name="destination")
    private String destination;

    @NotNull
    @Column(name="status")
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    @NotNull
    @Column(name="current_location")
    private String currentLocation;

    @NotNull
    @Column(name="created_at")
    private LocalDateTime createdAt;
}

