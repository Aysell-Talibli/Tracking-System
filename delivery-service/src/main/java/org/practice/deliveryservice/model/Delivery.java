package org.practice.deliveryservice.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.practice.deliveryservice.model.enums.DeliveryStatus;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="deliveries", schema = "delivery_service")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @NotNull
//    @Column(name="package_id", unique = true)
//    private String packageId; // Unique tracking code like "PKG123"

    @NotNull
    @Column(name="tracking_number", unique = true)
    private String tracking_number;

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

